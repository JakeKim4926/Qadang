package com.ssafy.cadang.request;

import lombok.Getter;

@Getter
public class MakeRecordRequestDTO {
    private String cafeName;
    private String drinkName;
    private double drinkCaffeine;
    private double drinkSugar;
}
