package com.ssafy.cadang.controller;

import com.ssafy.cadang.domain.Cafes;
import com.ssafy.cadang.domain.Drinks;
import com.ssafy.cadang.repository.CafeRepository;
import com.ssafy.cadang.repository.DrinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/drinks")
public class DrinkController {

    private final CafeRepository cafeRepository;
    private final DrinkRepository drinkRepository;
    @GetMapping("/cafe")
    public List<Cafes> cafeList(){
        return cafeRepository.findAll();
    }

    @GetMapping("/drink")
    public List<Drinks> drinksList(@RequestParam Long cafeId){
        return drinkRepository.findByCafeId(cafeId);
    }
}
