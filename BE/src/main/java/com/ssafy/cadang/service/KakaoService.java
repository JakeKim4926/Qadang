package com.ssafy.cadang.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.cadang.domain.User;
import com.ssafy.cadang.dto.IdResponse;
import com.ssafy.cadang.dto.KakaoInfo;
import com.ssafy.cadang.dto.KakaoOut;
import com.ssafy.cadang.dto.KakaoToken;
import com.ssafy.cadang.jwt.JwtLogin;
import com.ssafy.cadang.repository.NicknameRepository;
import com.ssafy.cadang.repository.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.SignatureException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
//@Transactional
public class KakaoService {

    private final UserRepository userRepository;
    private final NicknameRepository nickNameRepository;

    @Value("${spring.registration.kakao.client-id}")
    private String client_id;

    @Value("${spring.registration.kakao.client-secret}")
    private String client_secret;

    @Value("${spring.registration.kakao.redirect-uri}")
    private String redirect_uri;

    @Value("${spring.provider.kakao.token-uri}")
    private String token_uri;

    @Value("${spring.provider.kakao.user-info-uri}")
    private String user_info_uri;

    @Value("${spring.jwt.secretKey}")
    private String secretKey;

    @Value("${spring.registration.kakao.logout-uri}")
    private String logout_uri;

    @Value("${spring.provider.kakao.unlink-uri}")
    private String unlink_uri;

    @Value("${spring.registration.kakao.admin-key}")
    private String admin_key;


    // 프론트에서 보낸 인가코드를 사용해서 카카오에게 엑세스 토큰 요청하기
    public KakaoToken getAccessToken(String code) {

        // 요청 param ( body )
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", client_id);
        params.add("redirect_uri", redirect_uri);
        params.add("code", code);
        params.add("client_secret", client_secret);

        //request
        WebClient wc = WebClient.create(token_uri);
        String response = wc.post()
                .uri(token_uri)
                .body(BodyInserters.fromFormData(params))
                .header("Content-type", "application/x-www-form-urlencoded;charset=utf-8") //요청 헤더
                .retrieve()
                .bodyToMono(String.class)
                .block();

        //json형태로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        KakaoToken kakaoToken = null;

        try {
            kakaoToken = objectMapper.readValue(response, KakaoToken.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }



        return kakaoToken;
    }


    // 카카오로부터 받은 엑세스 토큰을 사용하여 사용자 정보 가져오기
    public KakaoInfo requestInfo(String access_token) {

        // HTTP 요청
        WebClient wc = WebClient.create(user_info_uri);

        String response = wc.get()
                .uri(user_info_uri)
                .header("Authorization", "Bearer " + access_token)
                .header("Content-type", "application/x-www-form-urlencoded;charset=utf-8") // 요청 헤더
                .retrieve()
                .bodyToMono(String.class)
                .block();

        ObjectMapper objectMapper = new ObjectMapper();
        KakaoInfo userinfo = null;

        try {
            userinfo = objectMapper.readValue(response, KakaoInfo.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return userinfo;
    }


    // 카카오 로그인 회원가입 + 사용자 정보 가져오기
    // 회원이면 가입된 회원인지 확인 후, 카카오 토큰 만료시키고 user 정보 리턴
    // 미가입 회원이면, 유저 정보를 추가한 후 토큰 만료시키고 user 정보 리턴
    @Transactional
    public User addUser(KakaoToken token, JwtLogin jwtLogin) {

        KakaoInfo info = requestInfo(token.getAccess_token()); // 사용자 정보 가져오기
        User user = userRepository.findByUserId(info.getId()); // 가입된 회원인지 확인하기

        // 최초 연동시 회원가입
        if (user == null) {

            // 닉네임
            String adjective = nickNameRepository.findRandomAdjective();
            String noun = nickNameRepository.findRandomNoun();
            String nickname = adjective+" "+noun;
            while (userRepository.findByUserName(nickname) != null) {

                adjective = nickNameRepository.findRandomAdjective();
                noun = nickNameRepository.findRandomNoun();
                nickname = adjective+" "+noun;

            }

            // 유저 정보 입력
            user = User.builder()
                    .userId(info.getId()) // 카카오 회원 식별번호
                    .userName(nickname) // 랜덤닉네임
                    .userCaffeine(400) // 성인 기준 카페인 권장량
                    .userSugar(50) // 20대 성인 여성 기준 당 권장량
                    .registerDatetime(info.getConnected_at()) // 연동일자
//                    .jwtRefreshToken(jwtRefreshToken) // jwt refresh token
                    .build();

            userRepository.save(user);

            getJwtRefresh(user);
        } else {
            jwtLogin.setIsUser(1);
        }

        kakaoLogout(token.getAccess_token()); // 카카오 엑세스 토큰 만료

        return user;
    }

    // jwtAccessToken 발급
    public String getJwtAccess(User user) { // jwttoken 최초 발급

        // jwtAccessToken 발급
        String jwtAccessToken = Jwts.builder()
                .setHeaderParam("type", "jwt") //Header 설정부분
                .setHeaderParam("alg", "HS256") //Header 설정부분
                .claim("userId", user.getUserId()) // Payload 설정부분
                .setExpiration(new Date(System.currentTimeMillis() + 1 * (1000 * 60 * 60 * 24))) // 만료시간 : 24시간
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
                .compact();

        return jwtAccessToken;
    }

    // jwtRefreshToken 발급
    public String getJwtRefresh(User addUser) {

        String jwtRefreshToken = Jwts.builder()
                .setHeaderParam("type", "jwt") //Header 설정부분
                .setHeaderParam("alg", "HS256") //Header 설정부분
                .claim("userId", addUser.getUserId()) // Payload 설정부분
                .setExpiration(new Date(System.currentTimeMillis() + 1 * (1000 * 60 * 60 * 24 * 14))) // 만료시간 : 14일
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
                .compact();

        addUser.setJwtRefreshToken(jwtRefreshToken);

        userRepository.save(addUser);

        return jwtRefreshToken;
    }

    ///////////////////////// 유효성 검사 ///////////////////////////////

    // 헤더에서 토큰 추출
    public String getJwtToken(String token) { // null 이면 실패
//        String token = request.getHeader("Authorization");
//        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
//        this.key = Keys.hmacShaKeyFor(keyBytes);

//        if (token != null && token.startsWith("Bearer ")) { // 헤더에 토큰이 있고 Bearer가 붙어있으면
//            return token.substring(7); // "Bearer " 다음의 문자열이 토큰이므로 추출
        if (token != null) { // 헤더에 토큰이 있고 Bearer가 붙어있으면
            return token; // "Bearer " 다음의 문자열이 토큰이므로 추출
        }
        return null; // 헤더에 token이 없거나 올바른 형식이 아니면 null 반환
    }

    // JWT 토큰  만료시간 검증
    public boolean validToken(String token) { // true면 실패
        try {
            Jwts.parser()
                    .setSigningKey(secretKey.getBytes())
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration()
                    .before(new Date());
            return false; // 만료일자가 현재시간 이후이면 유효함

        } catch (Exception e) { // 복호화 과정에서 에러가 나면 유효하지 않은 토큰

            return true; // 만료일자가 현재시간보다 이전이면 유효하지 않음

        }
    }

    // JWT 토큰 유효성 검사
    public Boolean vaildation(String token) { // false이면 실패
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SignatureException e) {
            return false; // 실패
        } catch (ExpiredJwtException e) {
            return false;
        } catch (MalformedJwtException e) {
            return false;
        } catch (IllegalArgumentException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
        return true; // 성공
    }

    // 리프레시 토큰 유효성 검사
    public boolean refreshcheck(String accesstoken) { // false이면 실패
        Long id = getUser(accesstoken).getUserId();
        User user = userRepository.findByUserId(id);
        if (user.getJwtRefreshToken().isEmpty()) { // refresh token 없음 => 새로운 유저 or logout
            return false;
        } else {
            return true;
        }

    }

    // JWT 토큰 유효성 검사 함수
    public String checkToken(String token) {
        String accesstoken = getJwtToken(token); // JWT 토큰이 헤더에 있는지 없는지 확인하고 추출
        if (accesstoken == null) { // 토큰이 헤더에 없거나 잘못된 형태
            return null;
        }

        boolean isExpire = validToken(accesstoken); // JWT 만료 여부 검사 ( true이면 실패 )
        if (isExpire == true) { // access 토큰이 만료되었으면
//            boolean isRefresh = refreshcheck(accesstoken); // refresh token 이 존재하는지 확인
//            if (isRefresh == false) { // refresh token이 없으면 실패
//                return null;
//            } else {
//                // 엑세스 토큰 갱신 => refresh 회원번호랑 토큰의 회원번호가 일치하면
//                Long accessUser = getUserId(accesstoken);
//                Long refreshUser = getUserId(userRepository.findByUserId(accessUser).getJwtRefreshToken());
//                if (accessUser == refreshUser) { // refresh 토큰 안의 회원번호와 access 토큰 안의 회원번호가 일치하면
//                    //refresh 토큰이 유효한지 검사하고 유효하면 갱신
//                    boolean refreshExpire = validToken(userRepository.findByUserId(refreshUser).getJwtRefreshToken());
//                    if (refreshExpire == true) {
//                        // 갱신
//                        String access = getJwtAccess(userRepository.findByUserId(refreshUser));
//                        token = access;
//                        return token;
//                    } else {
//                        return null;
//                    }
//                } else {
//                    return null;
//                }
//            }
            return null;
        }

        boolean isVaildation = vaildation(accesstoken); // JWT 토큰 유효성 검사 ( false이면 실패 )
        if (isVaildation == false) { // 토큰이 유효하지 않음
            return null;
        }
        return token;
    }

///////////////////////////////////////////////////////

    // JWT 토큰 복호화 및 유저아이디 가져오기
    public Long getUserId(String token) {
        Long getId = Jwts.parser()
                .setSigningKey(secretKey.getBytes()) // 비밀키를 이용해서 복호화
                .parseClaimsJws(token)
                .getBody()
                .get("userId", Long.class);

        return getId;

    }

    // JWT 토큰 복호화 및 유저정보 가져오기
    public User getUser(String token) {
        Long getId = Jwts.parser()
                .setSigningKey(secretKey.getBytes()) // 비밀키를 이용해서 복호화
                .parseClaimsJws(token)
                .getBody()
                .get("userId", Long.class);

        User user = userRepository.findByUserId(getId);
        return user;

    }



    // 카카오 엑세스 토큰 만료
    public IdResponse kakaoLogout(String code) {

        // 요청 param ( body )
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", client_id);
        params.add("logout_uri", logout_uri);
        params.add("client_secret", client_secret);

        //request
        WebClient wc = WebClient.create(unlink_uri);
        String response = wc.post()
                .uri(logout_uri)
                .header("Authorization", "Bearer " + code)
                .header("Content-type", "application/x-www-form-urlencoded;charset=utf-8") // 요청 헤더
                .retrieve()
                .bodyToMono(String.class)
                .block();

        //json형태로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        IdResponse id = null;

        try {
            id = objectMapper.readValue(response, IdResponse.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return id;

    }

    // 카카오 연동 끊기 ( admin key 이용 )
    public void EndKakao(Long userid){

        // 요청 param ( body )
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("target_id_type", "user_id");
        params.add("target_id",userid);

//        Map<String, Object> params = new HashMap<>();
//        params.put("target_id_type","user_id");
//        params.put("target_id",userid);

        //request
        WebClient wc = WebClient.create(unlink_uri);
        KakaoOut kakaoOut  = wc.post()
                .uri(unlink_uri)
                .body(BodyInserters.fromMultipartData(params))
//                .bodyValue()
                .header("Authorization", "KakaoAK " + admin_key)
                .header("Content-type", "application/x-www-form-urlencoded;charset=utf-8") // 요청 헤더
                .retrieve()
                .bodyToMono(KakaoOut.class)
                .block();
    }

//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE+";charset=UTF-8");
//        headers.add("Authorization", " KakaoAK " + admin_key);
//
//        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
//        params.set("target_id_type", "user_id");
//        params.set("target_id", userid);
//
//        org.springframework.http.HttpEntity<MultiValueMap<String, Object>> restRequest = new org.springframework.http.HttpEntity<>(params, headers);
//        ResponseEntity<JSONObject> apiResponse = restTemplate.postForEntity(uri, restRequest, JSONObject.class);



    public String revokeScopes(Long userid) {
        String url = "https://kapi.kakao.com/v2/user/revoke/scopes";
        String serviceAppAdminKey = admin_key;
        String targetId = String.valueOf(userid);
        String scopes = "[\"account_email\"]";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/x-www-form-urlencoded");
        headers.set("Authorization", "KakaoAK " + serviceAppAdminKey);

        String requestBody = "target_id_type=user_id&target_id=" + targetId + "&scopes=" + scopes;
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        return response.getBody();

    }


}