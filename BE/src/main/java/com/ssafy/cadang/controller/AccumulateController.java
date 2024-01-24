package com.ssafy.cadang.controller;

import com.ssafy.cadang.domain.AccumulatePK;
import com.ssafy.cadang.domain.Accumulates;
import com.ssafy.cadang.response.DurationAccumulateResponseDTO;
import com.ssafy.cadang.response.TodayAccumulateResponseDTO;
import com.ssafy.cadang.service.AccumulateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/records")
public class AccumulateController {

    private final AccumulateService accumulateService;
    @GetMapping("/today")
    public TodayAccumulateResponseDTO ReadTodayAccumulate(){
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
                .accumulateCaffeine(accumulate.getAccumulateCaffeine()).build();
    }

//    @GetMapping("/duration")
//    public List<DurationAccumulateResponseDTO> ReadDurationAccumulate(){
//
//    }
}
