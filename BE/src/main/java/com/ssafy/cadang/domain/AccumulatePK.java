package com.ssafy.cadang.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AccumulatePK implements Serializable {
    private Long userId;
    private LocalDate accumulateDate;

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if( o == null || getClass() != o.getClass()) return false;
        AccumulatePK accumulate1 = (AccumulatePK) o;
        return userId == accumulate1.userId && accumulateDate == accumulate1.accumulateDate;
    }
}
