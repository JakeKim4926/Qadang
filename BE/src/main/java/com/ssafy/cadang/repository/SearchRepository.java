package com.ssafy.cadang.repository;

import com.ssafy.cadang.domain.SearchLogs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchRepository extends JpaRepository<SearchLogs,Long> {
}
