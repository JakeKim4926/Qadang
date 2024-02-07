package com.ssafy.cadang.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtLogin {

    private String accesstoken; // jwt access token
    private int isUser; // 최초 : 0 , 기존 : 1
    private int isLogin; // 로그인 성공 여부 ( 0 : 실패, 1 : 성공 )
}
