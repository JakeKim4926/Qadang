package com.ssafy.cadang.repository;

import com.ssafy.cadang.domain.AccumulatePK;
import com.ssafy.cadang.domain.Accumulates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface AccumulateRepository extends JpaRepository<Accumulates, AccumulatePK> {

    Accumulates findByAccumulatePK(AccumulatePK accumulatePK);

    @Query("SELECT b FROM Accumulates b " +
            "WHERE b.accumulatePK.userId = :userId " +
            "AND b.accumulatePK.accumulateDate BETWEEN :start AND :end")
    List<Accumulates> findAllByUserIdAndAccumulateDateBetween(Long userId, LocalDate start, LocalDate end);

    @Query("SELECT b FROM Accumulates b " +
            "WHERE b.accumulatePK.accumulateDate =:date")
    List<Accumulates> findByDay(LocalDate date);
}
