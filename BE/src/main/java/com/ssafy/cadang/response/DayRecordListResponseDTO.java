package com.ssafy.cadang.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class DayRecordListResponseDTO {
    private double recordId;
    private double drinkId;
    private String cafeName;
    private String drinkName;
    private double drinkCaffeine;
    private double drinkSugar;
    private String drinkUrl;
    private int plusShot;
    private int plusSyrup;
}
