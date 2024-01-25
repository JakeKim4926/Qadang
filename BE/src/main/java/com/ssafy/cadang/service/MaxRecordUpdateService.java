package com.ssafy.cadang.service;

import com.ssafy.cadang.domain.Accumulates;
import com.ssafy.cadang.repository.AccumulateRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class MaxRecordUpdateService {

    private final AccumulateRepository accumulateRepository;

    // 매일 오후 0시에 실행
    @Scheduled(cron = "0 0 0 * * *")
    public void run() {
        System.out.println("Hello World!");
        LocalDate localDate = LocalDate.now().minusDays(1);
        List<Accumulates> accumulatesList =accumulateRepository.findByDay(localDate);

        //user max 갱신
        for(Accumulates accum : accumulatesList){
            //user max update
            System.out.println("Hello World!");
        }
    }
}