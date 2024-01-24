package com.ssafy.cadang.request;

import lombok.Getter;

@Getter
public class DrinkRecordRequestDTO {
    private Long drinkId;
    private String cafeName;
    private String drinkName;
    private int plusShot;
    private int plusSyrup;
}
