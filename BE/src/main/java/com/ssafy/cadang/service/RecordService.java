package com.ssafy.cadang.service;

import com.ssafy.cadang.domain.Records;
import com.ssafy.cadang.repository.RecordReporsitory;
import com.ssafy.cadang.response.DayRecordListResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.util.ObjectUtils.isEmpty;

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

    public List<DayRecordListResponseDTO> readDayRecord(Long userId, LocalDate localDate) {
        List<Records> records = recordReporsitory.findByUserIdAndRecordDate(userId, localDate);
        List<DayRecordListResponseDTO> result = new ArrayList<>();
        for (Records record : records){
            //나만의 음료
            if(isEmpty(record.getDrinkUrl())){
                result.add(DayRecordListResponseDTO.builder()
                        .recordId(record.getRecordId())
                        .cafeName(record.getCafeName())
                        .drinkName(record.getDrinkName())
                        .drinkCaffeine(record.getDrinkCaffeine())
                        .drinkSugar(record.getDrinkSugar())
                        .build());
            }
            else{
                //카페 음료
                result.add(DayRecordListResponseDTO.builder()
                        .recordId(record.getRecordId())
                        .drinkId(record.getDrinkId())
                        .cafeName(record.getCafeName())
                        .drinkName(record.getDrinkName())
                        .drinkCaffeine(record.getDrinkCaffeine())
                        .drinkSugar(record.getDrinkSugar())
                        .drinkUrl(record.getDrinkUrl())
                        .plusShot(record.getPlusShot())
                        .plusSyrup(record.getPlusSyrup())
                        .build());
            }
        }
        return result;
    }
}
