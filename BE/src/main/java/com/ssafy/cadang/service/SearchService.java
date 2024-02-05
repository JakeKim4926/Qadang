package com.ssafy.cadang.service;

import com.ssafy.cadang.domain.Cafes;
import com.ssafy.cadang.domain.Drinks;
import com.ssafy.cadang.domain.Records;
import com.ssafy.cadang.repository.CafeRepository;
import com.ssafy.cadang.repository.DrinkRepository;
import com.ssafy.cadang.response.DayRecordListResponseDTO;
import com.ssafy.cadang.response.DrinkResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final DrinkRepository drinkRepository;

    // 음료 전체 리스트
    public List<DrinkResponseDTO> drinkList() {
        List<Drinks> drinks = drinkRepository.findAll();
        List<DrinkResponseDTO> list = new ArrayList<>();

        for (Drinks drink : drinks) {
            list.add(DrinkResponseDTO.builder()
                            .drinkId(drink.getDrinkId())
                    .cafeId(drink.getCafeId())
                            .cafeName(drink.getCafeName())
                            .drinkName(drink.getDrinkName())
                            .drinkCaffeine(drink.getDrinkCaffeine())
                            .drinkSugar(drink.getDrinkSugar())
                            .drinkMl(drink.getDrinkMl())
                            .drinkOz(drink.getDrinkOz())
                    .drinkCalorie(drink.getDrinkCalorie())
                    .drinkNatrium(drink.getDrinkNatrium())
                    .drinkProtein(drink.getDrinkProtein())
                    .drinkUrl(drink.getDrinkUrl())
                    .drinkAllergy(drink.getDrinkAllergy())
                    .build());
        }
        return list;
    }

    // 음료 키워드 검색
//    public List<DrinkResponseDTO> keywordList(String keyword){
//
//        List<Drinks> list = drinkRepository.findByFullNameContaining(keyword);
//        List<DrinkResponseDTO> result = new ArrayList<>();
//
//        for (Drinks drink : list) {
//            result.add(DrinkResponseDTO.builder()
//                    .drinkId(drink.getDrinkId())
//                    .cafeId(drink.getCafeId())
//                    .cafeName(drink.getCafeName())
//                    .drinkName(drink.getDrinkName())
//                    .drinkCaffeine(drink.getDrinkCaffeine())
//                    .drinkSugar(drink.getDrinkSugar())
//                    .drinkMl(drink.getDrinkMl())
//                    .drinkOz(drink.getDrinkOz())
//                    .drinkCalorie(drink.getDrinkCalorie())
//                    .drinkNatrium(drink.getDrinkNatrium())
//                    .drinkProtein(drink.getDrinkProtein())
//                    .drinkUrl(drink.getDrinkUrl())
//                    .drinkAllergy(drink.getDrinkAllergy())
//                    .build());
//        }
//        return result;
//
//
//    }
}