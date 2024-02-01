package com.ssafy.cadang.service;

import com.ssafy.cadang.domain.AccumulatePK;
import com.ssafy.cadang.domain.Accumulates;
import com.ssafy.cadang.dto.Facts;
import com.ssafy.cadang.repository.AccumulateRepository;
import com.ssafy.cadang.response.DayAccumulateResponseDTO;
import com.ssafy.cadang.response.DurationAccumulateResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccumulateService {
    private final AccumulateRepository accumulateRepository;

    public void create(Accumulates accumulate) {
        accumulateRepository.save(accumulate);
    }

    public Accumulates read(AccumulatePK accumulatePK) {
        return accumulateRepository.findByAccumulatePK(accumulatePK);
    }

    public void addRecord(Long userId, Facts facts) {
        //acumulate
        AccumulatePK accumulatePK = new AccumulatePK(userId, LocalDate.now());
        Accumulates accumulate = read(accumulatePK);

        // Accumulate가 생성 되어 있을 경우
        if(accumulate != null){
            //PK가 존재?
            accumulate = Accumulates.builder()
                    .accumulatePK(accumulatePK)
                    .accumulateCalorie(accumulate.getAccumulateCalorie() + facts.getCalorie())
                    .accumulateCaffeine(accumulate.getAccumulateCaffeine() + facts.getCaffeine())
                    .accumulateSugar(accumulate.getAccumulateSugar() + facts.getSugar())
                    .build();
        }
        else{
            accumulate = Accumulates.builder()
                    .accumulatePK(accumulatePK)
                    .accumulateCalorie(facts.getCalorie())
                    .accumulateCaffeine(facts.getCaffeine())
                    .accumulateSugar(facts.getSugar())
                    .build();
        }
        create(accumulate);
    }

    public Accumulates readDate(Long uId) {
        AccumulatePK accumulatePK = new AccumulatePK(uId, LocalDate.now());
        return accumulateRepository.findByAccumulatePK(accumulatePK);
    }

    public Accumulates readDay(Long userId, LocalDate day) {
        AccumulatePK accumulatePK = new AccumulatePK(userId, day);
        return accumulateRepository.findByAccumulatePK(accumulatePK);
    }

    public List<DurationAccumulateResponseDTO> readDuration(Long userId) {
        LocalDate start = LocalDate.now().minusMonths(3);
        LocalDate end = LocalDate.now();

        List<Accumulates> accumulates = accumulateRepository.findAllByUserIdAndAccumulateDateBetween(userId,start,end);

        return transDurationDto(accumulates);
    }

    public List<DurationAccumulateResponseDTO> readMonth(Long userId, YearMonth ym) {
        LocalDate start = ym.atDay(1);
        LocalDate end = ym.atEndOfMonth();

        List<Accumulates> accumulates = accumulateRepository.findAllByUserIdAndAccumulateDateBetween(userId,start,end);
        return transDurationDto(accumulates);
    }

    public List<DurationAccumulateResponseDTO> transDurationDto(List<Accumulates> accumulates){
        List<DurationAccumulateResponseDTO> result = new ArrayList<>();
        for(Accumulates accum : accumulates){
            result.add(DurationAccumulateResponseDTO.builder()
                    .accumulateDate(accum.getAccumulatePK().getAccumulateDate())
                    .accumulateCaffeine(accum.getAccumulateCaffeine())
                    .accumulateSugar(accum.getAccumulateSugar())
                    .build());
        }
        return result;
    }
}
