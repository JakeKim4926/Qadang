package com.ssafy.cadang.controller;

import com.ssafy.cadang.domain.User;
import com.ssafy.cadang.dto.KakaoToken;
import com.ssafy.cadang.repository.UserRepository;
import com.ssafy.cadang.service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final UserRepository userRepository;
    private final KakaoService kakaoService;

    // 엑세스 토큰 요청
//    @GetMapping("/kakao-login")
//    public Map<String,String> KakaoLogin(@RequestParam("code") String code) {
//        // 엑세스 토큰 받기
//        KakaoToken oauthToken = userService.getAccessToken(code);
//
//        // 유저가 없으면 회원가입 + 사용자 정보 요청
//        User addUser = userService.addUser(oauthToken.getAccess_token());
//    }

    // 직접 인가 코드 받기 ( test )
    @GetMapping("/kakao-login")
    public String KakaoLogin(@RequestParam("code") String code) {

        // 엑세스 토큰 받기
        KakaoToken oauthToken = kakaoService.getAccessToken(code);

        System.out.println("토큰 : " + oauthToken);

        // 유저가 없으면 회원가입 + 사용자 정보 요청
//        User addUser = kakaoService.addUser(oauthToken.getAccess_token());
        User addUser = kakaoService.addUser(oauthToken);
        System.out.println("사용자 정보 요청 완료");


        return "카카오 로그인 인증 완료, code= : " + code;

    }
}