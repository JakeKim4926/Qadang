package com.ssafy.cadang.service;

import com.ssafy.cadang.domain.Records;
import com.ssafy.cadang.repository.RecordReporsitory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecordService {

    private final RecordReporsitory recordReporsitory;
    public void create(Records record) {
        recordReporsitory.save(record);
    }

    public Records readRecord(Long recordId) {
        return recordReporsitory.findByRecordId(recordId);
    }

    public void delete(Long recordId) {
        recordReporsitory.deleteById(recordId);
    }
}
