package com.ssafy.cadang.service;

import com.ssafy.cadang.domain.Drinks;
import com.ssafy.cadang.repository.DrinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DrinkService {

    private final DrinkRepository drinkRepository;
    public Drinks readDrink(Long drinkId){
        return drinkRepository.findByDrinkId(drinkId);
    }

    public List<Drinks> drinkList(Long cafeId) {
        return drinkRepository.findByCafeId(cafeId);
    }

    public Drinks getDrinkId(Long id){
        return drinkRepository.findByDrinkId(id);
    }

    public List<Drinks> drinkCafeKeyword(Long cafeId, String keyword) {
        return drinkRepository.findByCafeIdAndDrinkNameContaining(cafeId,keyword);
    }
}
