package com.ssafy.cadang.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KakaoOut {

    public String target_id_type;
    public Long target_id;



}
