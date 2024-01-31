package com.ssafy.cadang.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatForwardDTO {
        private String userName; // nickname
        private Long senderId; // 채팅을 보낸 사람
        private String message; // 메시지
    }
