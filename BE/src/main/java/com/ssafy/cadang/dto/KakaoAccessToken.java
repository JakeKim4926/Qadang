package com.ssafy.cadang.dto;

import lombok.Data;

@Data
public class KakaoAccessToken {

    public Long id; // 회원번호 :: 필수
    public int expires_in; // 엑세스 토큰 만료 시간 (초) :: 필수
    public int app_id; // 앱 ID :: 필수
}
