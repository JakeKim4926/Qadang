package com.ssafy.cadang.domain;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Accumulates {

    @EmbeddedId
    private AccumulatePK accumulatePK;

    private double accumulateCaffeine;
    private double accumulateSugar;
    private double accumulateCalorie;
}
