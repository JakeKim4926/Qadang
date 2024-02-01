package com.ssafy.cadang.controller;

import com.ssafy.cadang.domain.ChatRooms;
import com.ssafy.cadang.repository.CafeRepository;
import com.ssafy.cadang.response.ChatRoomResponseDTO;
import com.ssafy.cadang.response.DayRecordListResponseDTO;
import com.ssafy.cadang.service.CafeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatController {
    private final CafeService cafeService;
    @GetMapping("/chatlist")
    public ResponseEntity<List<ChatRoomResponseDTO>> chatList(){
        //user check

        return new ResponseEntity<List<ChatRoomResponseDTO>>(cafeService.getList(), HttpStatus.OK);
    }

    @GetMapping("/chatroom")
    public void chatRoom(@RequestParam Long cafeId){

    }
}
