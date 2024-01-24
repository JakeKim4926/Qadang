package com.ssafy.cadang.request;

import lombok.Getter;

@Getter
public class DrinkRecordUpdateRequestDTO {
    private Long recordId;
    private Long drinkId;
    private String cafeName;
    private String drinkName;
    private int plusShot;
    private int plusSyrup;
}
