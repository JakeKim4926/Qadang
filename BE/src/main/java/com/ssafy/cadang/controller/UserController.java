package com.ssafy.cadang.controller;

import com.ssafy.cadang.domain.User;
import com.ssafy.cadang.dto.KakaoToken;
import com.ssafy.cadang.service.KakaoService;
import com.ssafy.cadang.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final KakaoService kakaoService;
    private final UserService userService;

    // 프론트에서 인가코드 받기
//    @GetMapping("/kakao-login")
//    public ResponseEntity KakaoLogin(@RequestParam("code") String code) {
//        // 엑세스 토큰 받기
//        KakaoToken oauthToken = userService.getAccessToken(code);
//
//        // 유저가 없으면 회원가입 + 사용자 정보 요청
//        User addUser = userService.addUser(oauthToken.getAccess_token());

//    return ResponseEntity.ok().headers(headers).body("success");
//    }

    // 직접 인가 코드 받기 ( test )
    @GetMapping("/kakao-login")
    public String KakaoLogin(@RequestParam("code") String code) {
//    public ResponseEntity KakaoLogin(@RequestParam("code") String code) {

        // 엑세스 토큰 받기
        KakaoToken oauthToken = kakaoService.getAccessToken(code);

        System.out.println("토큰 : " + oauthToken);
        {

            // 유저가 없으면 회원가입 + 사용자 정보 요청
            User addUser = kakaoService.addUser(oauthToken);
            System.out.println("사용자 정보 요청 완료");

            // 카카오에서 발급받은 access token을 이용해서 jwt 토큰 발급받기
            String jwtAccessToken = kakaoService.getJwtAccess(oauthToken.getAccess_token());
            String jwtRefreshToken = kakaoService.getJwtRefresh(addUser);
            System.out.println("jwt " + jwtAccessToken + " / " +jwtRefreshToken);

            // 헤더에 access token 담기
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + jwtAccessToken);

//            return ResponseEntity.ok().headers(headers).body("success");
            return "success";
        }
    }

    // 회원정보조회
    @GetMapping("/api/user")
    private ResponseEntity<?> userInfo(HttpServletRequest request){

        // 헤더에서 jwt access 토큰 추출
        String jwtAccess = kakaoService.getJwtToken(request);

        // jwt 토큰이 유효한지 검증
        boolean isVaild = kakaoService.validToken(jwtAccess);

        // jwt 유효하면 카카오 access를 이용해서 정보 조회
        // jwt 유효하지 않다면 갱신 => 카카오랑 jwt 모두 같이 갱신
        if(isVaild == true){

            // jwt token에 담겨있던 kakaoaccess token을 이용해 회원 아이디 얻기
            Long id = kakaoService.kakaoVaild(jwtAccess).getId();

            // 아이디를 이용해 회원찾기
            User user = userService.findUser(id);

            // jwt 토큰 갱신
            jwtAccess = kakaoService.getJwtAccess(user,)

        }
            User user = kakaoService.getUser(jwtAccess);

    }


}