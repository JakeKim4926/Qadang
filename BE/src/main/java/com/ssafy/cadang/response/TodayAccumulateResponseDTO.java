package com.ssafy.cadang.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TodayAccumulateResponseDTO {
    private double userCaffeine;
    private double userSugar;
    private double accumulateCaffeine;
    private double accumulateSugar;
    private double accumulateCalorie;
}
