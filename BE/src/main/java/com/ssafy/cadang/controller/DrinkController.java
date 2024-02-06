package com.ssafy.cadang.controller;

import com.ssafy.cadang.domain.Cafes;
import com.ssafy.cadang.domain.Drinks;
import com.ssafy.cadang.repository.CafeRepository;
import com.ssafy.cadang.repository.DrinkRepository;
import com.ssafy.cadang.service.CafeService;
import com.ssafy.cadang.service.DrinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/drinks")
public class DrinkController {

    private final CafeService cafeService;
    private final DrinkService drinkService;
    @GetMapping("/cafe")
    public List<Cafes> cafeList(){
        return cafeService.cafeList();
    }

    @GetMapping("{cafeId}")
    public List<Drinks> drinksList(@PathVariable Long cafeId){
        //user check
        return drinkService.drinkList(cafeId);
    }

    @GetMapping("/{cafeId}/{keyword}")
    public List<Drinks> drinksList(@PathVariable Long cafeId, @PathVariable String keyword){
        //user check
        return drinkService.drinkCafeKeyword(cafeId,keyword);
    }
}
