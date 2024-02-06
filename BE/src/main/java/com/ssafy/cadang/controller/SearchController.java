package com.ssafy.cadang.controller;

import com.ssafy.cadang.domain.Cafes;
import com.ssafy.cadang.domain.Drinks;
import com.ssafy.cadang.domain.User;
import com.ssafy.cadang.dto.UserAmount;
import com.ssafy.cadang.response.DrinkResponseDTO;
import com.ssafy.cadang.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SearchController {

    private final KakaoService kakaoService;
    private final SearchService searchService;
    private final SearchLogService searchLogService;

    // 검색량 높은 키워드 순위
    // 기록량 높은 음료 순위
    // 음료 전체보기
    @GetMapping("/drinks")
    public ResponseEntity<?> allDrink(@RequestHeader("Authorization") String token) {

        String jwt = kakaoService.getJwtToken(token);
        User user = kakaoService.getUser(jwt);

        List<DrinkResponseDTO> list = searchService.drinkList();

        return new ResponseEntity<List<DrinkResponseDTO>>(list, HttpStatus.OK);
    }

    // 음료 검색하기
    @GetMapping("/search/{keyword}")
    public ResponseEntity<?> searchList(@PathVariable String keyword ) {
        //user check
        Long userId = 1L;
        if(userId == 0)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        searchLogService.log(userId,keyword);
        List<DrinkResponseDTO> list = searchService.keywordList(keyword);

        return new ResponseEntity<List<DrinkResponseDTO>>(list, HttpStatus.OK);
    }
}
