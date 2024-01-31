package com.ssafy.cadang.request;

import lombok.Getter;

@Getter
public class MakeRecordUpdateRequestDTO {
    private Long recordId;
    private String cafeName;
    private String drinkName;
    private double drinkCaffeine;
    private double drinkSugar;
}
