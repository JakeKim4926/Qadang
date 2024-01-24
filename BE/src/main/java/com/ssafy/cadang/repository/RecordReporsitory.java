package com.ssafy.cadang.repository;

import com.ssafy.cadang.domain.Records;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordReporsitory extends JpaRepository<Records, Long> {

    Records findByRecordId(Long recordId);
}
