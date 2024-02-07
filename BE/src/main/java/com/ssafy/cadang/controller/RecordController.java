package com.ssafy.cadang.controller;

import com.ssafy.cadang.domain.Drinks;
import com.ssafy.cadang.domain.Records;
import com.ssafy.cadang.dto.Facts;
import com.ssafy.cadang.request.DrinkRecordRequestDTO;
import com.ssafy.cadang.request.DrinkRecordUpdateRequestDTO;
import com.ssafy.cadang.request.MakeRecordRequestDTO;
import com.ssafy.cadang.request.MakeRecordUpdateRequestDTO;
import com.ssafy.cadang.response.DayRecordListResponseDTO;
import com.ssafy.cadang.service.AccumulateService;
import com.ssafy.cadang.service.DrinkService;
import com.ssafy.cadang.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/records")
public class RecordController {

    private final RecordService recordService;
    private final DrinkService drinkService;
    private final AccumulateService accumulateService;

    //카페 기록 생성 drink
    @PostMapping("/drink")
    public ResponseEntity<HttpStatus> drinkRecordCreate(@RequestBody DrinkRecordRequestDTO drinkRecordDTO){
        //user check
        Long userId = 1L;
        if(userId == 0)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        //drink exist check
        Drinks drink = drinkService.readDrink(drinkRecordDTO.getDrinkId());
        if(drink == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);


        Records record = Records.builder()
                .recordDate(LocalDate.now())
                .userId(userId)
                .drinkId(drink.getDrinkId())
                .cafeName(drink.getCafeName())
                .drinkName(drink.getDrinkName())
                .drinkCaffeine(drink.getDrinkCaffeine())
                .drinkSugar(drink.getDrinkSugar())
                .drinkCal(drink.getDrinkCalorie())
                .drinkUrl(drink.getDrinkUrl())
                .plusShot(drinkRecordDTO.getPlusShot())
                .plusSyrup(drinkRecordDTO.getPlusSyrup()).build();

        Facts facts = Facts.builder()
                //.calorie(drink.getDrinkCalorie())
                .caffeine(drink.getDrinkCaffeine() + drinkRecordDTO.getPlusShot() * 75.0)
                .sugar(drink.getDrinkSugar() + drinkRecordDTO.getPlusSyrup() * 6.0)
                .build();

        recordService.create(record);
        accumulateService.addRecord(userId,facts);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    //나만의 음료 기록 생성 make
    @PostMapping("/make")
    public ResponseEntity<HttpStatus> makeRecordCreate(@RequestBody MakeRecordRequestDTO makeRecordRequestDTO){
        //user check
        Long userId = 1L;
        if(userId == 0)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        Records record = Records.builder()
                .userId(userId)
                .recordDate(LocalDate.now())
                .cafeName(makeRecordRequestDTO.getCafeName())
                .drinkName(makeRecordRequestDTO.getDrinkName())
                .drinkCaffeine(makeRecordRequestDTO.getDrinkCaffeine())
                .drinkSugar(makeRecordRequestDTO.getDrinkSugar()).build();

        Facts facts = Facts.builder()
               // .calorie(0)
                .caffeine(makeRecordRequestDTO.getDrinkCaffeine())
                .sugar(makeRecordRequestDTO.getDrinkSugar())
                .build();

        recordService.create(record);
        accumulateService.addRecord(userId,facts);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/drink")
    public ResponseEntity<HttpStatus> drinkRecordUpdate(@RequestBody DrinkRecordUpdateRequestDTO drinkRecordUpdateRequestDTO){
        //user check
        Long userId = 1L;
        if(userId == 0)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        //record update auth check
        Records record = recordService.readRecord(drinkRecordUpdateRequestDTO.getRecordId());
        if(record == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        if(!userId.equals(record.getUserId()))
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        //drink exist check
        Drinks drink = drinkService.readDrink(drinkRecordUpdateRequestDTO.getDrinkId());
        if(drink == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);


        double nowCaffeine = drink.getDrinkCaffeine() + drinkRecordUpdateRequestDTO.getPlusShot() * 75.0;
        double nowSugar = drink.getDrinkSugar() + drinkRecordUpdateRequestDTO.getPlusSyrup() * 6.0;
        double lastCaffeine = record.getDrinkCaffeine() + record.getPlusShot() * 75.0;
        double lastSugar = record.getDrinkSugar() + record.getPlusSyrup() * 6.0;
        Facts facts = Facts.builder()
               // .calorie(drink.getDrinkCalorie())
                .caffeine(nowCaffeine - lastCaffeine)
                .sugar(nowSugar - lastSugar)
                .build();

        record = Records.builder()
                .recordId(drinkRecordUpdateRequestDTO.getRecordId())
                .recordDate(LocalDate.now())
                .userId(userId)
                .drinkId(drink.getDrinkId())
                .cafeName(drink.getCafeName())
                .drinkName(drink.getDrinkName())
                .drinkCaffeine(drink.getDrinkCaffeine())
                .drinkSugar(drink.getDrinkSugar())
                .drinkCal(drink.getDrinkCalorie())
                .drinkUrl(drink.getDrinkUrl())
                .plusShot(drinkRecordUpdateRequestDTO.getPlusShot())
                .plusSyrup(drinkRecordUpdateRequestDTO.getPlusSyrup()).build();


        recordService.create(record);
        accumulateService.addRecord(userId,facts);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/make")
    public ResponseEntity<HttpStatus> makeRecordUpdate(@RequestBody MakeRecordUpdateRequestDTO makeRecordUpdateRequestDTO){
        //user check
        Long userId = 1L;
        if(userId == 0)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        //record update auth check
        Records record = recordService.readRecord(makeRecordUpdateRequestDTO.getRecordId());
        if(record == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        if(!userId.equals(record.getUserId()))
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);


        double nowCaffeine = makeRecordUpdateRequestDTO.getDrinkCaffeine();
        double nowSugar = makeRecordUpdateRequestDTO.getDrinkSugar();
        double lastCaffeine = record.getDrinkCaffeine();
        double lastSugar = record.getDrinkSugar();

        Facts facts = Facts.builder()
                // .calorie(drink.getDrinkCalorie())
                .caffeine(nowCaffeine - lastCaffeine)
                .sugar(nowSugar - lastSugar)
                .build();

        record = Records.builder()
                .recordId(makeRecordUpdateRequestDTO.getRecordId())
                .recordDate(LocalDate.now())
                .userId(userId)
                .cafeName(makeRecordUpdateRequestDTO.getCafeName())
                .drinkName(makeRecordUpdateRequestDTO.getDrinkName())
                .drinkCaffeine(makeRecordUpdateRequestDTO.getDrinkCaffeine())
                .drinkSugar(makeRecordUpdateRequestDTO.getDrinkSugar()).build();


        recordService.create(record);
        accumulateService.addRecord(userId,facts);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{recordId}")
    public ResponseEntity<HttpStatus> drinkRecordDelete(@PathVariable Long recordId){
        //user check
        Long userId = 1L;
        if(userId == 0)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        //record delete auth check
        Records record = recordService.readRecord(recordId);
        if(record == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        if(!userId.equals(record.getUserId()))
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        double caffeine = 0.0;
        double sugar = 0.0;
        if(record.getDrinkId()!=null){ //cafe drink
            caffeine = (record.getDrinkCaffeine() + record.getPlusShot() * 75.0) * -1.0;
            sugar = (record.getDrinkSugar() + record.getPlusSyrup() * 6.0) * -1.0;
        }else {     //make drink
            caffeine = record.getDrinkCaffeine() * -1.0;
            sugar = record.getDrinkSugar() * -1.0;
        }

        Facts facts = Facts.builder()
                //.calorie(drink.getDrinkCalorie())
                .caffeine(caffeine)
                .sugar(sugar)
                .build();

        recordService.delete(recordId);
        accumulateService.addRecord(userId,facts);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{date}/day")
    public ResponseEntity<List<DayRecordListResponseDTO>> drinkListRead(@PathVariable String date){
        //user check
        Long userId = 1L;
        if(userId == 0)
            return new ResponseEntity<List<DayRecordListResponseDTO>>((List<DayRecordListResponseDTO>) null,HttpStatus.UNAUTHORIZED);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate localDate = LocalDate.parse(date, formatter);

        return new ResponseEntity<List<DayRecordListResponseDTO>>(recordService.readDayRecord(userId,localDate), HttpStatus.OK);
    }
}
