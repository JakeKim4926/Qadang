package com.ssafy.cadang.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatMessageId;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime sendTime;

    private Long senderId;
    private Long chatRoomId;
}