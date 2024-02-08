package com.ssafy.cadang.controller;

import com.ssafy.cadang.domain.User;
import com.ssafy.cadang.response.ChatMessageResponseDTO;
import com.ssafy.cadang.response.ChatRoomResponseDTO;
import com.ssafy.cadang.service.CafeService;
import com.ssafy.cadang.service.KakaoService;
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
    private final KakaoService kakaoService;
    @GetMapping("/chatroom")
    public ResponseEntity<List<ChatRoomResponseDTO>> chatList(@RequestHeader("Authorization") String token){
        //user check
        String passAccess = kakaoService.checkToken(token); // 통과한 access token
        if (passAccess == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        User user = kakaoService.getUser(passAccess);

        return new ResponseEntity<List<ChatRoomResponseDTO>>(cafeService.getList(), HttpStatus.OK);
    }

    @GetMapping("/{chatId}")
    public ResponseEntity<List<ChatMessageResponseDTO>> chatRoom(@RequestHeader("Authorization") String token,@PathVariable Long chatId){
        //user check
        String passAccess = kakaoService.checkToken(token); // 통과한 access token
        if (passAccess == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        User user = kakaoService.getUser(passAccess);


        return new ResponseEntity<List<ChatMessageResponseDTO>>(messageService.messageList(chatId), HttpStatus.OK);
    }
}
