package com.ssafy.cadang.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Facts {
    private double caffeine;
    private double sugar;
    private double calorie;
}
