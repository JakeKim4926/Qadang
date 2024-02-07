package com.ssafy.cadang.service;

import com.ssafy.cadang.domain.Drinks;
import com.ssafy.cadang.domain.Records;
import com.ssafy.cadang.repository.DrinkRepository;
import com.ssafy.cadang.repository.RecordReporsitory;
import com.ssafy.cadang.response.DayRecordListResponseDTO;
import com.ssafy.cadang.response.DrinkResponseDTO;
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
    private final DrinkRepository drinkRepository;
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

    public List<DrinkResponseDTO> readRank() {
        List<Long> rank = recordReporsitory.findByRank();
        List<DrinkResponseDTO> result = new ArrayList<>();
        for(Long id : rank){
            Drinks drink = drinkRepository.findByDrinkId(id);
            result.add(DrinkResponseDTO.builder()
                    .drinkId(drink.getDrinkId())
                    .drinkCaffeine(drink.getDrinkCaffeine())
                    .drinkSugar(drink.getDrinkSugar())
                    .drinkName(drink.getDrinkName())
                    .cafeName(drink.getCafeName())
                    .drinkUrl(drink.getDrinkUrl())
                    .drinkMl(drink.getDrinkMl())
                    .drinkOz(drink.getDrinkOz())
                    .drinkCalorie(drink.getDrinkCalorie())
                    .drinkNatrium(drink.getDrinkNatrium())
                    .drinkProtein(drink.getDrinkProtein())
                    .drinkFat(drink.getDrinkFat())
                    .drinkAllergy(drink.getDrinkAllergy())
                    .build());
        }
        return result;
    }
}
