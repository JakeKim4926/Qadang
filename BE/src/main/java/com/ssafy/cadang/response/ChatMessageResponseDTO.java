package com.ssafy.cadang.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class ChatMessageResponseDTO {
    private String userName;
    private String message;
    private LocalDateTime time;
}
