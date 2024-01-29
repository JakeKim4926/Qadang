package com.ssafy.cadang.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @NotNull
    private Long userId;

    private String userName;
    private String userEmail; // 필요할까..?
    private Date userBirth;
    private int userHeight;
    private int userweight;
    private double userCaffeine;
    private double userSugar;
    private int userGender; // 남자(1), 여자(2)
    private Date registerDatetime;
    private int userHealth; // 활동량 : 1~4
    private double maxSugarValue;
    private double maxCaffeineValue;
    private Date maxSugarDate;
    private Date maxCaffeineDate;
    private String refreshToken;

}
