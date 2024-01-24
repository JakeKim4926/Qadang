package com.ssafy.cadang.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

public class UserController {

    // 프론트에서 넘겨준 인가코드 이용해서 토큰 정보 받기
    @ResponseBody
    @GetMapping("/api/user/social-login")
    public void kakao(@RequestParam String code){
        String accessToken = userService.getKaKaoAccessToken(code);
    }
}
