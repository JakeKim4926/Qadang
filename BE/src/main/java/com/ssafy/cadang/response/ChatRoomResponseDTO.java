package com.ssafy.cadang.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ChatRoomResponseDTO {
    private Long chatroomId;
    private String chatroomName;
}
