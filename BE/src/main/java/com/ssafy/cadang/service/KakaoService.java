package com.ssafy.cadang.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.cadang.domain.User;
import com.ssafy.cadang.dto.IdResponse;
import com.ssafy.cadang.dto.KakaoInfo;
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
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Date;

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

        System.out.println("[로그인] 엑세스 토큰 요청 시작");

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

        System.out.println(" requestInfo / 정보 가져오기 ");

        // HTTP 요청
        WebClient wc = WebClient.create(user_info_uri);

        System.out.println(" requestInfo / webclient 시작 ");

        String response = wc.get()
                .uri(user_info_uri)
                .header("Authorization", "Bearer " + access_token)
                .header("Content-type", "application/x-www-form-urlencoded;charset=utf-8") // 요청 헤더
                .retrieve()
                .bodyToMono(String.class)
                .block();

        System.out.println(" requestInfo / response ");


        ObjectMapper objectMapper = new ObjectMapper();
        KakaoInfo userinfo = null;

        System.out.println(" requestInfo / 정보담기 ");


        try {

            System.out.println(" requestInfo / try response ");

            userinfo = objectMapper.readValue(response, KakaoInfo.class);
        } catch (JsonProcessingException e) {

            System.out.println(" requestInfo / catch ");

            e.printStackTrace();
        }

        System.out.println(" requestInfo / 성공 " + userinfo);

        return userinfo;
    }


    // 카카오 로그인 회원가입 + 사용자 정보 가져오기
    // 회원이면 가입된 회원인지 확인 후, 카카오 토큰 만료시키고 user 정보 리턴
    // 미가입 회원이면, 유저 정보를 추가한 후 토큰 만료시키고 user 정보 리턴
    @Transactional
    public User addUser(KakaoToken token, JwtLogin jwtLogin) {

        System.out.println("addUser / 사용자 정보 가져오기");

        KakaoInfo info = requestInfo(token.getAccess_token()); // 사용자 정보 가져오기

        System.out.println(" addUser / 회원 확인하기 ");
        System.out.println("사용자 회원 번호 : " + info.getId());
        User user = userRepository.findByUserId(info.getId()); // 가입된 회원인지 확인하기


        // 최초 연동시 회원가입
        if (user == null) {

            System.out.println(" addUser / 없는 회원이므로 회원가입 ");


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

            System.out.println(" addUser / 회원 정보담기");


            userRepository.save(user);
            System.out.println(" addUser / 회원 추가하기 ");

            getJwtRefresh(user);
        } else {
            jwtLogin.setIsUser(1);
        }

        System.out.println(" addUser / 성공 ");

        kakaoLogout(token.getAccess_token()); // 카카오 엑세스 토큰 만료
        System.out.println("카카오 만료");

        return user;
    }

    // jwtAccessToken 발급
    public String getJwtAccess(User user) { // jwttoken 최초 발급

        System.out.println("getJwt / 들어옴 ");
        // jwtAccessToken 발급
        String jwtAccessToken = Jwts.builder()
                .setHeaderParam("type", "jwt") //Header 설정부분
                .setHeaderParam("alg", "HS256") //Header 설정부분
                .claim("userId", user.getUserId()) // Payload 설정부분
                .setExpiration(new Date(System.currentTimeMillis() + 1 * (1000 * 60 * 60 * 24))) // 만료시간 : 24시간
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
                .compact();

        System.out.println("jwt / 엑세스 ");
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

        System.out.println("시스템 현재 시간 : "+System.currentTimeMillis());

        addUser.setJwtRefreshToken(jwtRefreshToken);

        userRepository.save(addUser);

        System.out.println("jwt / 리프레시");

        return jwtRefreshToken;
    }

    ///////////////////////// 유효성 검사 ///////////////////////////////

    // 헤더에서 토큰 추출
    public String getJwtToken(String token) { // null 이면 실패
//        String token = request.getHeader("Authorization");
//        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
//        this.key = Keys.hmacShaKeyFor(keyBytes);
        System.out.println("[유효성검사1] 헤더토큰추출 " + token);
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
            System.out.println("[유효성검사2] 토큰 만료시간 검증");
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
            System.out.println("[유효성검사3] 토큰 유효성검사");
            Jwts.parserBuilder()
                    .setSigningKey(secretKey.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SignatureException e) {
            System.out.println("여기1");
            return false; // 실패
        } catch (ExpiredJwtException e) {
            System.out.println("여기2");
            return false;
        } catch (MalformedJwtException e) {
            System.out.println("여기3");
            return false;
        } catch (IllegalArgumentException e) {
            System.out.println("여기4");
            return false;
        } catch (Exception e) {
            System.out.println("여기5");
            return false;
        }
        return true; // 성공
    }

    // 리프레시 토큰 유효성 검사
    public boolean refreshcheck(String accesstoken) { // false이면 실패
        System.out.println("refresh 유효성 검사");
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
            boolean isRefresh = refreshcheck(accesstoken); // refresh token 이 존재하는지 확인
            if (isRefresh == false) { // refresh token이 없으면 실패
                return null;
            } else {
                // 엑세스 토큰 갱신 => refresh 회원번호랑 토큰의 회원번호가 일치하면
                Long accessUser = getUserId(accesstoken);
                Long refreshUser = getUserId(userRepository.findByUserId(accessUser).getJwtRefreshToken());
                if (accessUser == refreshUser) { // refresh 토큰 안의 회원번호와 access 토큰 안의 회원번호가 일치하면
                    //refresh 토큰이 유효한지 검사하고 유효하면 갱신
                    boolean refreshExpire = validToken(userRepository.findByUserId(refreshUser).getJwtRefreshToken());
                    if (refreshExpire == true) {
                        // 갱신
                        String access = getJwtAccess(userRepository.findByUserId(refreshUser));
                        token = access;
                        return token;
                    } else {
                        return null;
                    }
                } else {
                    return null;
                }
            }
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

    // 카카오 연동 끊기
    public void EndKakao(Long userid){

        // 요청 param ( body )
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("target_id_type", "user_id");
        params.add("target_id", String.valueOf(userid));

        //request
        WebClient wc = WebClient.create(unlink_uri);
        String response = wc.post()
                .uri(unlink_uri)
                .header("Authorization", "Bearer " + admin_key)
                .header("Content-type", "application/x-www-form-urlencoded;charset=utf-8") // 요청 헤더
                .retrieve()
                .bodyToMono(String.class)
                .block();

        //json형태로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        Long id = null;

        try {
            id = objectMapper.readValue(response, Long.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}