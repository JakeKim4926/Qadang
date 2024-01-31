package com.ssafy.cadang.dto;

import lombok.Data;

import java.util.Date;

@Data
public class KakaoInfo {

    public Long id; // 회원번호
    public Date connected_at; // 서비스에 연결 완료된 시각 ( UTC )
    public KakaoAccount kakao_account;
}
