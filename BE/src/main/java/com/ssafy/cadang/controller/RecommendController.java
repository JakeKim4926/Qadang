package com.ssafy.cadang.controller;

import com.ssafy.cadang.domain.Drinks;
import com.ssafy.cadang.domain.Records;
import com.ssafy.cadang.domain.User;
import com.ssafy.cadang.response.DrinkResponseDTO;
import com.ssafy.cadang.response.RecentRecordDTO;
import com.ssafy.cadang.service.DrinkService;
import com.ssafy.cadang.service.KakaoService;
import com.ssafy.cadang.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recommend")
public class RecommendController {
    private final DrinkService drinkService;
    private final RecordService recordService;
    private final KakaoService kakaoService;

    @GetMapping("record")
    public ResponseEntity<RecentRecordDTO> recentRecord(@RequestHeader("Authorization") String token){
        String passAccess = kakaoService.checkToken(token); // 통과한 access token
        if (passAccess == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        User user = kakaoService.getUser(passAccess);

        // 가장 최근 기록 가져오기
        Records records = recordService.readLastRecord(user.getUserId());
        if(records == null){
            return new ResponseEntity<>(null, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(
                    RecentRecordDTO.builder().drinkName(records.getDrinkName()).build()
                    ,HttpStatus.OK);
        }
    }

    @GetMapping("caffeine")
    public ResponseEntity<List<DrinkResponseDTO>> recommendCaffeine(@RequestHeader("Authorization") String token) throws MalformedURLException, URISyntaxException {
        String passAccess = kakaoService.checkToken(token); // 통과한 access token
        if (passAccess == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        User user = kakaoService.getUser(passAccess);

        // 가장 최근 기록 가져오기
        Records records = recordService.readLastRecord(user.getUserId());
        // is null?
        Drinks drinkFirst,drinkSecond;
        if(records == null){
            drinkFirst = drinkService.getDrinkId(1275L);
            drinkSecond = drinkService.getDrinkId(294L);
        }else {
            URL url = new URL("http://127.0.0.1:5000/caffeine?num=" + String.valueOf(records.getRecordId()));
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(url.toURI(), String.class);
            String responseBody = responseEntity.getBody();
            String[] strings = responseBody.split(",");
            Long first = Long.parseLong(strings[0]);
            Long second = Long.parseLong(strings[1]);
            drinkFirst = drinkService.getDrinkId(first);
            drinkSecond = drinkService.getDrinkId(second);
        }

        List<DrinkResponseDTO> result = new ArrayList<>();
        result.add(
                DrinkResponseDTO.builder()
                        .drinkId(drinkFirst.getDrinkId())
                        .drinkCaffeine(drinkFirst.getDrinkCaffeine())
                        .drinkSugar(drinkFirst.getDrinkSugar())
                        .drinkName(drinkFirst.getDrinkName())
                        .cafeName(drinkFirst.getCafeName())
                        .drinkUrl(drinkFirst.getDrinkUrl())
                        .drinkMl(drinkFirst.getDrinkMl())
                        .drinkOz(drinkFirst.getDrinkOz())
                        .drinkCalorie(drinkFirst.getDrinkCalorie())
                        .drinkNatrium(drinkFirst.getDrinkNatrium())
                        .drinkProtein(drinkFirst.getDrinkProtein())
                        .drinkFat(drinkFirst.getDrinkFat())
                        .drinkAllergy(drinkFirst.getDrinkAllergy())
                        .build()
        );
        result.add(
                DrinkResponseDTO.builder()
                        .drinkId(drinkSecond.getDrinkId())
                        .drinkCaffeine(drinkSecond.getDrinkCaffeine())
                        .drinkSugar(drinkSecond.getDrinkSugar())
                        .drinkName(drinkSecond.getDrinkName())
                        .cafeName(drinkSecond.getCafeName())
                        .drinkUrl(drinkSecond.getDrinkUrl())
                        .drinkMl(drinkSecond.getDrinkMl())
                        .drinkOz(drinkSecond.getDrinkOz())
                        .drinkCalorie(drinkSecond.getDrinkCalorie())
                        .drinkNatrium(drinkSecond.getDrinkNatrium())
                        .drinkProtein(drinkSecond.getDrinkProtein())
                        .drinkFat(drinkSecond.getDrinkFat())
                        .drinkAllergy(drinkSecond.getDrinkAllergy())
                        .build()
        );

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("sugar")
    public ResponseEntity<List<DrinkResponseDTO>> recommendSugar(@RequestHeader("Authorization") String token) throws MalformedURLException, URISyntaxException {
        String passAccess = kakaoService.checkToken(token); // 통과한 access token
        if (passAccess == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        User user = kakaoService.getUser(passAccess);

        // 가장 최근 기록 가져오기
        Records records = recordService.readLastRecord(user.getUserId());
        // is null?
        Drinks drinkFirst,drinkSecond;
        if(records == null){
            drinkFirst = drinkService.getDrinkId(1275L);
            drinkSecond = drinkService.getDrinkId(294L);
        }else {
            URL url = new URL("http://127.0.0.1:5000/sugar?num=" + String.valueOf(records.getRecordId()));
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(url.toURI(), String.class);
            String responseBody = responseEntity.getBody();
            String[] strings = responseBody.split(",");
            Long first = Long.parseLong(strings[0]);
            Long second = Long.parseLong(strings[1]);
            drinkFirst = drinkService.getDrinkId(first);
            drinkSecond = drinkService.getDrinkId(second);
        }

        List<DrinkResponseDTO> result = new ArrayList<>();
        result.add(
                DrinkResponseDTO.builder()
                        .drinkId(drinkFirst.getDrinkId())
                        .drinkCaffeine(drinkFirst.getDrinkCaffeine())
                        .drinkSugar(drinkFirst.getDrinkSugar())
                        .drinkName(drinkFirst.getDrinkName())
                        .cafeName(drinkFirst.getCafeName())
                        .drinkUrl(drinkFirst.getDrinkUrl())
                        .drinkMl(drinkFirst.getDrinkMl())
                        .drinkOz(drinkFirst.getDrinkOz())
                        .drinkCalorie(drinkFirst.getDrinkCalorie())
                        .drinkNatrium(drinkFirst.getDrinkNatrium())
                        .drinkProtein(drinkFirst.getDrinkProtein())
                        .drinkFat(drinkFirst.getDrinkFat())
                        .drinkAllergy(drinkFirst.getDrinkAllergy())
                        .build()
        );
        result.add(
                DrinkResponseDTO.builder()
                        .drinkId(drinkSecond.getDrinkId())
                        .drinkCaffeine(drinkSecond.getDrinkCaffeine())
                        .drinkSugar(drinkSecond.getDrinkSugar())
                        .drinkName(drinkSecond.getDrinkName())
                        .cafeName(drinkSecond.getCafeName())
                        .drinkUrl(drinkSecond.getDrinkUrl())
                        .drinkMl(drinkSecond.getDrinkMl())
                        .drinkOz(drinkSecond.getDrinkOz())
                        .drinkCalorie(drinkSecond.getDrinkCalorie())
                        .drinkNatrium(drinkSecond.getDrinkNatrium())
                        .drinkProtein(drinkSecond.getDrinkProtein())
                        .drinkFat(drinkSecond.getDrinkFat())
                        .drinkAllergy(drinkSecond.getDrinkAllergy())
                        .build()
        );

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}