package com.ssafy.cadang.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class DurationAccumulateResponseDTO {
    private LocalDate accumulateDate;
    private double accumulateCaffeine;
    private double accumulateSugar;
}
