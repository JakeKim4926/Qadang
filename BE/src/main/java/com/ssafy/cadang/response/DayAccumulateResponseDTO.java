package com.ssafy.cadang.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DayAccumulateResponseDTO {
    private double accumulateCaffeine;
    private double accumulateSugar;
}
