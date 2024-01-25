package com.ssafy.cadang.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatController {
    @GetMapping("/chatlist")
    public void chatList(){

    }

    @GetMapping("/chatroom")
    public void chatRoom(@RequestParam Long cafeId){

    }
}
