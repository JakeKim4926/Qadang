package com.ssafy.cadang.repository;

import com.ssafy.cadang.domain.SearchLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface SearchRepository extends JpaRepository<SearchLogs,Long> {
    @Query("SELECT s.searchKeyword FROM SearchLogs s GROUP BY s.searchKeyword ORDER BY count(*) desc LIMIT 10")
    List<String> findByRank();
}