package com.ssafy.cadang.controller;

import com.ssafy.cadang.response.DrinkResponseDTO;
import com.ssafy.cadang.response.TodayAccumulateResponseDTO;
import com.ssafy.cadang.service.RecordService;
import com.ssafy.cadang.service.SearchLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rank")
public class RankController {
    private final RecordService recordService;
    private final SearchLogService searchLogService;

    @GetMapping("/keywordranking")
    public ResponseEntity<List<String>> keywordRank(){
        return new ResponseEntity<>(searchLogService.readRank(), HttpStatus.OK);
    }
    @GetMapping("/recordranking")
    public ResponseEntity<List<DrinkResponseDTO>> recordRank(){
        return new ResponseEntity<>(recordService.readRank(), HttpStatus.OK);
    }
}
