package com.ssafy.cadang.controller;

import com.ssafy.cadang.domain.Cafes;
import com.ssafy.cadang.domain.Drinks;
import com.ssafy.cadang.domain.User;
import com.ssafy.cadang.dto.UserAmount;
import com.ssafy.cadang.response.DrinkResponseDTO;
import com.ssafy.cadang.service.CafeService;
import com.ssafy.cadang.service.DrinkService;
import com.ssafy.cadang.service.KakaoService;
import com.ssafy.cadang.service.SearchService;
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

    // 검색량 높은 키워드 순위
    // 기록량 높은 음료 순위
    // 음료 전체보기
    @GetMapping("/drinks")
    public ResponseEntity<?> alldrink(@RequestHeader("Authorization") String token) {

        // 토큰 유효성 검사
        String passAccess = kakaoService.checkToken(token); // 통과한 access token
        System.out.println(" 유효성검사 시작 "+passAccess);
        if (passAccess == null) {
            System.out.println("유효성검사 실패");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        List<DrinkResponseDTO> list = searchService.drinkList();

        return new ResponseEntity<List<DrinkResponseDTO>>(list, HttpStatus.OK);
    }

    // 음료 검색하기
    @GetMapping("/search")
    public ResponseEntity<?> searchlist(@RequestHeader("Authorization") String token, @PathVariable String keyword ) {
        // 토큰 유효성 검사
        String passAccess = kakaoService.checkToken(token); // 통과한 access token
        System.out.println(" 유효성검사 시작 "+passAccess);
        if (passAccess == null) {
            System.out.println("유효성검사 실패");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        List<DrinkResponseDTO> list = searchService.keywordList(keyword);

        return new ResponseEntity<List<DrinkResponseDTO>>(list, HttpStatus.OK);
    }
}
