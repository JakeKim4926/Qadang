package com.ssafy.cadang.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserAmount {

    public double userCaffeine;
    public double userSugar;

}
