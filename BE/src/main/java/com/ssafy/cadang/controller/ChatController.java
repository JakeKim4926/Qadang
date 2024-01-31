package com.ssafy.cadang.controller;

import com.ssafy.cadang.domain.ChatMessages;
import com.ssafy.cadang.domain.ChatRooms;
import com.ssafy.cadang.repository.CafeRepository;
import com.ssafy.cadang.response.ChatMessageResponseDTO;
import com.ssafy.cadang.response.ChatRoomResponseDTO;
import com.ssafy.cadang.response.DayRecordListResponseDTO;
import com.ssafy.cadang.service.CafeService;
import com.ssafy.cadang.service.MessageService;
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
    private final MessageService messageService;
    @GetMapping("/chatroom")
    public ResponseEntity<List<ChatRoomResponseDTO>> chatList(){
        //user check

        return new ResponseEntity<List<ChatRoomResponseDTO>>(cafeService.getList(), HttpStatus.OK);
    }

    @GetMapping("/chatcontent")
    public ResponseEntity<List<ChatMessageResponseDTO>> chatRoom(@RequestParam Long chatId){
        //user check

        return new ResponseEntity<List<ChatMessageResponseDTO>>(messageService.messageList(chatId), HttpStatus.OK);
    }
}
