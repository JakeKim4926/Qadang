package com.ssafy.cadang.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

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
    @Setter
    private int userBirth; // 생년
    @Setter
    private int userHeight; // 키
    @Setter
    private int userWeight; // 몸무게
    @Setter
    private int userGender; // 남자(1), 여자(2)
    @Setter
    private int userHealth; // 활동량 : 1~4 (낮은순)
    private Date registerDatetime; // 연동일자
    @Setter
    private double userCaffeine; // 카페인 권장량
    @Setter
    private double userSugar; // 당 권장량
    @Setter
    private double maxCaffeineValue; // 카페인 최고 섭취량
    @Setter
    private double maxSugarValue; // 당 최고 섭취량
    @Setter
    private LocalDate maxCaffeineDate; // 카페인 최고량 섭취 일자
    @Setter
    private LocalDate maxSugarDate; // 당 최고량 섭취 일자
    @Setter
    private String jwtRefreshToken; // jwt refresh token

}
