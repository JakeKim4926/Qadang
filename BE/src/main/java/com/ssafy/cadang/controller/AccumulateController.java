package com.ssafy.cadang.controller;

import com.ssafy.cadang.domain.Accumulates;
import com.ssafy.cadang.domain.User;
import com.ssafy.cadang.response.DayAccumulateResponseDTO;
import com.ssafy.cadang.response.DurationAccumulateResponseDTO;
import com.ssafy.cadang.response.TodayAccumulateResponseDTO;
import com.ssafy.cadang.service.AccumulateService;
import com.ssafy.cadang.service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final KakaoService kakaoService;
    @GetMapping("/today")
    public ResponseEntity<TodayAccumulateResponseDTO> readTodayAccumulate(@RequestHeader("Authorization") String token){
        //user check
        String passAccess = kakaoService.checkToken(token); // 통과한 access token
        if (passAccess == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        User user = kakaoService.getUser(passAccess);

        Accumulates accumulate = accumulateService.readDate(user.getUserId());
        if(accumulate == null)
            return  new ResponseEntity<>(
                    TodayAccumulateResponseDTO.builder()
                    .accumulateSugar(0)
                    .accumulateCaffeine(0).build()
                    ,HttpStatus.OK
            );

        return  new ResponseEntity<>(
                TodayAccumulateResponseDTO.builder()
                .accumulateSugar(accumulate.getAccumulateSugar())
                .accumulateCaffeine(accumulate.getAccumulateCaffeine())
                .userCaffeine(user.getUserCaffeine())
                .userSugar(user.getUserCaffeine())
                .build()
                ,HttpStatus.OK
        );
    }

    @GetMapping("/duration")
    public ResponseEntity<List<DurationAccumulateResponseDTO>> readDurationAccumulate(@RequestHeader("Authorization") String token){
        //user check
        String passAccess = kakaoService.checkToken(token); // 통과한 access token
        if (passAccess == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        User user = kakaoService.getUser(passAccess);

        return new ResponseEntity<>(accumulateService.readDuration(user.getUserId()),HttpStatus.OK);
    }

    @GetMapping("/{ym}/month") //202303
    public ResponseEntity<List<DurationAccumulateResponseDTO>> readDurationAccumulate(@RequestHeader("Authorization") String token,@PathVariable String ym){
        //user check
        String passAccess = kakaoService.checkToken(token); // 통과한 access token
        if (passAccess == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        User user = kakaoService.getUser(passAccess);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
        YearMonth yearMonth = YearMonth.parse(ym, formatter);

        return new ResponseEntity<>(accumulateService.readMonth(user.getUserId(),yearMonth),HttpStatus.OK);
    }
    @GetMapping("/{date}/day")
    public ResponseEntity<DayAccumulateResponseDTO> readDayAccumulate(@RequestHeader("Authorization") String token,@PathVariable String date){
        //user check
        String passAccess = kakaoService.checkToken(token); // 통과한 access token
        if (passAccess == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        User user = kakaoService.getUser(passAccess);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate day = LocalDate.parse(date, formatter);

        Accumulates accumulate = accumulateService.readDay(user.getUserId(),day);
        if(accumulate == null)
            return new ResponseEntity<>(null,HttpStatus.OK);
        return new ResponseEntity<>(DayAccumulateResponseDTO.builder()
                .accumulateCaffeine(accumulate.getAccumulateCaffeine())
                .accumulateSugar(accumulate.getAccumulateSugar())
                .build(),HttpStatus.OK);
    }
}
