package com.ssafy.cadang.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInfo {

    public String userName;
    public int userBirth;
    public int userHeight;
    public int userWeight;
    public int userGender;
    public int userHealth;

}
