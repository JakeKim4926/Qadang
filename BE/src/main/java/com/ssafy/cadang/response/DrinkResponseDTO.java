package com.ssafy.cadang.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DrinkResponseDTO {
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
    private String cafeUrl;
}
