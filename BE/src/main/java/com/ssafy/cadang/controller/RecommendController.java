package com.ssafy.cadang.controller;

import com.ssafy.cadang.domain.Drinks;
import com.ssafy.cadang.domain.User;
import com.ssafy.cadang.response.DrinkResponseDTO;
import com.ssafy.cadang.service.DrinkService;
import com.ssafy.cadang.service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recommend")
public class RecommendController {
    private final DrinkService drinkService;
    private final KakaoService kakaoService;

    @GetMapping("caffeine")
    public ResponseEntity<DrinkResponseDTO> recommendCaffeine(@RequestHeader("Authorization") String token){
        String passAccess = kakaoService.checkToken(token); // 통과한 access token
        if (passAccess == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        User user = kakaoService.getUser(passAccess);
        Long userId = user.getUserId();

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
    public ResponseEntity<DrinkResponseDTO> recommendSugar(@RequestHeader("Authorization") String token){
        String passAccess = kakaoService.checkToken(token); // 통과한 access token
        if (passAccess == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        User user = kakaoService.getUser(passAccess);
        Long userId = user.getUserId();

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
