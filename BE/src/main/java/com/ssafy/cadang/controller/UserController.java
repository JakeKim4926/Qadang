package com.ssafy.cadang.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/user")
public class UserController {

    // 프론트로부터 인가코드 받기
    @ResponseBody
    @GetMapping("/social-login")
    public void KakaoCode(@RequestParam String code){
        System.out.printf("code : "+code);
    }
}


