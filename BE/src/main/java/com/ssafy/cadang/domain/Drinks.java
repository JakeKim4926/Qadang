package com.ssafy.cadang.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Drinks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long drinkId;

    private Long cafeId;

    private String cafeName;
    private String drinkName;
    private double drinkCaffeine;
    private double drinkSugar;
    private int drinkMl;
    private double drinkOz;
    private double drinkCalorie;
    private double drinkNatrium;
    private double drinkProtein;
    private double drinkFat;
    private String drinkUrl;
    private String drinkAllergy;
    private String drinkFullName;
    private String tag;
    private String cafeUrl;
}
