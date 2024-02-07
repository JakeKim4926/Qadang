package com.ssafy.cadang.controller;

import com.ssafy.cadang.domain.Drinks;
import com.ssafy.cadang.response.DrinkResponseDTO;
import com.ssafy.cadang.service.DrinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recommend")
public class RecommendController {
    private final DrinkService drinkService;

    @GetMapping("caffeine")
    public ResponseEntity<DrinkResponseDTO> recommendCaffeine(){
        Drinks drink = drinkService.getCaffeineRecommend();
        return new ResponseEntity<>(
                DrinkResponseDTO.builder()
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
                .build()
                , HttpStatus.OK
        );
    }

    @GetMapping("sugar")
    public ResponseEntity<DrinkResponseDTO> recommendSugar(){
        Drinks drink = drinkService.getSugarRecommend();
        return new ResponseEntity<>(
                DrinkResponseDTO.builder()
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
                        .build()
                , HttpStatus.OK
        );
    }
}
