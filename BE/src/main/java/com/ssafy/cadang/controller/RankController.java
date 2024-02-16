package com.ssafy.cadang.controller;

import com.ssafy.cadang.response.DrinkResponseDTO;
import com.ssafy.cadang.service.KakaoService;
import com.ssafy.cadang.service.RecordService;
import com.ssafy.cadang.service.SearchLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rank")
public class RankController {
    private final RecordService recordService;
    private final SearchLogService searchLogService;
    private final KakaoService kakaoService;
    @GetMapping("/keywordranking")
    public ResponseEntity<List<String>> keywordRank(@RequestHeader("Authorization") String token){
        //user check
        String passAccess = kakaoService.checkToken(token); // 통과한 access token
        if (passAccess == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(searchLogService.readRank(), HttpStatus.OK);
    }
    @GetMapping("/recordranking")
    public ResponseEntity<List<DrinkResponseDTO>> recordRank(@RequestHeader("Authorization") String token){
        //user check
        String passAccess = kakaoService.checkToken(token); // 통과한 access token
        if (passAccess == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(recordService.readRank(), HttpStatus.OK);
    }
}
