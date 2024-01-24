package com.ssafy.cadang.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Records {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recordId;

    private LocalDate recordDate;
    private Long userId;
    private Long drinkId;
    private String cafeName;
    private String drinkName;
    private double drinkCaffeine;
    private double drinkSugar;
    private double drinkCal;
    private String drinkUrl;
    private int plusShot;
    private int plusSyrup;
}
