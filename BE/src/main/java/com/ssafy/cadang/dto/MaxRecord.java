package com.ssafy.cadang.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class MaxRecord {

    public double maxSugarValue;
    public double maxCaffenineValue;
    public LocalDate maxSugarDate;
    public LocalDate maxCaffeineDate;
}
