package com.ssafy.cadang.controller;

import com.ssafy.cadang.domain.AccumulatePK;
import com.ssafy.cadang.domain.Accumulates;
import com.ssafy.cadang.domain.Records;
import com.ssafy.cadang.response.DayAccumulateResponseDTO;
import com.ssafy.cadang.response.DurationAccumulateResponseDTO;
import com.ssafy.cadang.response.TodayAccumulateResponseDTO;
import com.ssafy.cadang.service.AccumulateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accumulate")
public class AccumulateController {

    private final AccumulateService accumulateService;
    @GetMapping("/today")
    public TodayAccumulateResponseDTO readTodayAccumulate(){
        //user check
        Long userId = 1L;
        if(userId == 0)
            return null;

        Accumulates accumulate = accumulateService.readDate(userId);
        if(accumulate == null)
            return  TodayAccumulateResponseDTO.builder()
                    .accumulateSugar(0)
                    .accumulateCaffeine(0).build();

        return  TodayAccumulateResponseDTO.builder()
                .accumulateSugar(accumulate.getAccumulateSugar())
                .accumulateCaffeine(accumulate.getAccumulateCaffeine())
                //user caffeine
                //user sugar
                .build();
    }

    @GetMapping("/duration")
    public List<DurationAccumulateResponseDTO> readDurationAccumulate(){
        //user check
        Long userId = 1L;
        if(userId == 0)
            return null;

        return accumulateService.readDuration(userId);
    }

    @GetMapping("/month") //202303
    public List<DurationAccumulateResponseDTO> readDurationAccumulate(@RequestParam String ym){
        //user check
        Long userId = 1L;
        if(userId == 0)
            return null;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
        YearMonth yearMonth = YearMonth.parse(ym, formatter);

        return accumulateService.readMonth(userId,yearMonth);
    }
    @GetMapping("/day")
    public DayAccumulateResponseDTO readDayAccumulate(@RequestParam String date){
        //user check
        Long userId = 1L;
        if(userId == 0)
            return null;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate day = LocalDate.parse(date, formatter);

        Accumulates accumulate = accumulateService.readDay(userId,day);
        if(accumulate == null)
            return null;
        return DayAccumulateResponseDTO.builder()
                .accumulateCaffeine(accumulate.getAccumulateCaffeine())
                .accumulateSugar(accumulate.getAccumulateSugar())
                .build();
    }
}
