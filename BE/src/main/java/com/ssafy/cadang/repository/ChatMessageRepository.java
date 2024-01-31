package com.ssafy.cadang.repository;

import com.ssafy.cadang.domain.ChatMessages;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessages, Long> {

    List<ChatMessages> findByChatRoomIdOrderByTime(Long chatRoomId);
}
