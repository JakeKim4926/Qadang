package com.ssafy.cadang.repository;

import com.ssafy.cadang.domain.Records;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface RecordReporsitory extends JpaRepository<Records, Long> {

    Records findByRecordId(Long recordId);

    List<Records> findByUserIdAndRecordDate(Long userId, LocalDate localDate);

    @Query("SELECT r.drinkId FROM Records r WHERE r.drinkId IS NOT NULL " +
            "GROUP BY r.drinkId ORDER BY count(*) DESC LIMIT 10")
    List<Long> findByRank();

    public void deleteByUserId(Long userId);

    @Query("SELECT r " +
            "FROM Records r " +
            "where r.drinkId is not null " +
            "and r.userId =:userId " +
            "order by r.recordId DESC LIMIT 1")
    Records findUserRecent(Long userId);
}
