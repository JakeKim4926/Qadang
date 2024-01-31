package com.ssafy.cadang.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
//    @GeneratedValue(strategy = IDENTITY)
    @NotNull
    private Long userId;

    private String userName; // 닉네임
    private int userBirth; // 생년
    private int userHeight; // 키
    private int userweight; // 몸무게
    private int userGender; // 남자(1), 여자(2)
    private int userHealth; // 활동량 : 1~4 (낮은순)
    private LocalDate registerDatetime; // 연동일자
    private double userCaffeine; // 카페인 권장량
    private double userSugar; // 당 권장량
    @Setter
    private double maxCaffeineValue; // 카페인 최고 섭취량
    @Setter
    private double maxSugarValue; // 당 최고 섭취량
    @Setter
    private LocalDate maxCaffeineDate; // 카페인 최고량 섭취 일자
    @Setter
    private LocalDate maxSugarDate; // 당 최고량 섭취 일자
    private String kakaoRefreshToken; // kakao refresh token
    private String jwtRefreshToken; // jwt refresh token

}
