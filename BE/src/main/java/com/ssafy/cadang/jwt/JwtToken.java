package com.ssafy.cadang.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtToken {

    private String Authorization; // access 토큰 헤더 key
    private String RefreshToken; // refresh 토큰 헤더 key
}
