package com.ssafy.cadang.repository;

import com.ssafy.cadang.domain.AccumulatePK;
import com.ssafy.cadang.domain.Accumulates;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccumulateRepository extends JpaRepository<Accumulates, AccumulatePK> {

    Accumulates findByAccumulatePK(AccumulatePK accumulatePK);
}
