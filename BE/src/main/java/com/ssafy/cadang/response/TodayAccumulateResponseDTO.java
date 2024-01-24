package com.ssafy.cadang.response;

import lombok.Builder;

@Builder
public class TodayAccumulateResponseDTO {
    private double userCaffeine;
    private double userSugar;
    private double accumulateCaffeine;
    private double accumulateSugar;
    private double accumulateCalorie;
}
