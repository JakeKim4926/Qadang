package com.ssafy.cadang.service;

import com.ssafy.cadang.domain.Drinks;
import com.ssafy.cadang.repository.DrinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DrinkService {

    private final DrinkRepository drinkRepository;
     public Drinks readDrink(Long drinkId){
       return drinkRepository.findByDrinkId(drinkId);
    }
}
