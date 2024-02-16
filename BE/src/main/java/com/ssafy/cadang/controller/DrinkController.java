package com.ssafy.cadang.controller;

import com.ssafy.cadang.domain.Cafes;
import com.ssafy.cadang.domain.Drinks;
import com.ssafy.cadang.domain.User;
import com.ssafy.cadang.repository.CafeRepository;
import com.ssafy.cadang.repository.DrinkRepository;
import com.ssafy.cadang.service.CafeService;
import com.ssafy.cadang.service.DrinkService;
import com.ssafy.cadang.service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/drinks")
public class DrinkController {

    private final CafeService cafeService;
    private final DrinkService drinkService;
    private final KakaoService kakaoService;
    @GetMapping("/cafe")
    public ResponseEntity<List<Cafes>> cafeList(@RequestHeader("Authorization") String token){
        //user check
        String passAccess = kakaoService.checkToken(token); // 통과한 access token
        if (passAccess == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        User user = kakaoService.getUser(passAccess);

        return new ResponseEntity<>(cafeService.cafeList(),HttpStatus.OK);
    }

    @GetMapping("{cafeId}")
    public ResponseEntity<List<Drinks>> drinksList(@RequestHeader("Authorization") String token,@PathVariable Long cafeId){
        //user check
        String passAccess = kakaoService.checkToken(token); // 통과한 access token
        if (passAccess == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        User user = kakaoService.getUser(passAccess);

        return new ResponseEntity<>(drinkService.drinkList(cafeId),HttpStatus.OK);
    }

    @GetMapping("/{cafeId}/{keyword}")
    public ResponseEntity<List<Drinks>> drinksList(@RequestHeader("Authorization") String token,
                                   @PathVariable Long cafeId, @PathVariable String keyword){
        //user check
        String passAccess = kakaoService.checkToken(token); // 통과한 access token
        if (passAccess == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        User user = kakaoService.getUser(passAccess);

        return new ResponseEntity<>(drinkService.drinkCafeKeyword(cafeId,keyword),HttpStatus.OK);
    }
}
