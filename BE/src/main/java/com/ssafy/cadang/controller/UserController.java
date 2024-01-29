package com.ssafy.cadang.controller;

import com.ssafy.cadang.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserService userservice;

    @RequestMapping("/api/user/social-login")
    public String login(@RequestParam("code") String code) {
        String access_Token = userservice.getAccessToken(code);
        System.out.println("controller access_token : " + access_Token);

        return code;
    }
}