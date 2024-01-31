package com.ssafy.cadang.service;

import com.ssafy.cadang.domain.Accumulates;
import com.ssafy.cadang.domain.User;
import com.ssafy.cadang.repository.AccumulateRepository;
import com.ssafy.cadang.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class MaxRecordUpdateService {

    private final AccumulateRepository accumulateRepository;
    private final UserService userService;

    // 매일 오후 0시에 실행
    @Scheduled(cron = "0 0 0 * * *")
    public void run() {
        log.info("user max value update");
        LocalDate localDate = LocalDate.now().minusDays(1);
        List<Accumulates> accumulatesList =accumulateRepository.findByDay(localDate);

        //user max 갱신
        for(Accumulates accum : accumulatesList){
            //user max update
            log.info("accum update check - user : " + accum.getAccumulatePK().getUserId());
            double caffeine = accum.getAccumulateCaffeine();
            double sugar = accum.getAccumulateSugar();
            User user = userService.findUser(accum.getAccumulatePK().getUserId());

            if(Double.compare(caffeine, user.getMaxCaffeineValue()) > 0){
                user.setMaxCaffeineValue(caffeine);
                user.setMaxCaffeineDate(localDate);
            }
            if(Double.compare(sugar, user.getMaxSugarValue()) > 0){
                user.setMaxSugarValue(sugar);
                user.setMaxSugarDate(localDate);
            }
            userService.update(user);
        }
    }
}