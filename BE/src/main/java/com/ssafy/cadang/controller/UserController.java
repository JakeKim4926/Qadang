package com.ssafy.cadang.controller;

import com.ssafy.cadang.domain.User;
import com.ssafy.cadang.dto.KakaoToken;
import com.ssafy.cadang.jwt.JwtToken;
import com.ssafy.cadang.repository.UserRepository;
import com.ssafy.cadang.service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final UserRepository userRepository;
    private final KakaoService kakaoService;

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
            JwtToken jwtToken = kakaoService.getJwtToken(addUser);
            System.out.println("jwt " + jwtToken);

            // 헤더에 access token 담기
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + jwtToken.getAuthorization());

//            return ResponseEntity.ok().headers(headers).body("success");
            return "success";
        }


    }
}