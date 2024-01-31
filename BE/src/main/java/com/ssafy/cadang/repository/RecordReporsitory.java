package com.ssafy.cadang.repository;

import com.ssafy.cadang.domain.Records;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface RecordReporsitory extends JpaRepository<Records, Long> {

    Records findByRecordId(Long recordId);

    List<Records> findByUserIdAndRecordDate(Long userId, LocalDate localDate);
}
