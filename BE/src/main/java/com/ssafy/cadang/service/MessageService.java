package com.ssafy.cadang.service;

import com.ssafy.cadang.domain.ChatMessages;
import com.ssafy.cadang.dto.ChatForwardDTO;
import com.ssafy.cadang.dto.ChatMessageDTO;
import com.ssafy.cadang.repository.ChatMessageRepository;
import com.ssafy.cadang.response.ChatMessageResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final ChatMessageRepository chatMessageRepository;
    private final UserService userService;

    public ChatForwardDTO sendMessage(ChatMessageDTO chatMessageDTO) {
        String userName = userService.findUserName(chatMessageDTO.getSenderId());
        ChatMessages chatMessage = ChatMessages.builder()
                .chatRoomId(chatMessageDTO.getChatRoomId())
                .senderId(chatMessageDTO.getSenderId())
                .userName(userName)
                .content(chatMessageDTO.getMessage())
                .build();
        chatMessageRepository.save(chatMessage);
        return ChatForwardDTO.builder()
                .message(chatMessageDTO.getMessage())
                .senderId(chatMessageDTO.getSenderId())
                .userName(userName)
                .build();
    }

    public List<ChatMessageResponseDTO> messageList(Long chatRoomId){
        List<ChatMessageResponseDTO> result = new ArrayList<>();
        List<ChatMessages> findList = chatMessageRepository.findByChatRoomIdOrderByTime(chatRoomId);
        for(ChatMessages m : findList){
            result.add(
                    ChatMessageResponseDTO.builder()
                            .message(m.getContent())
                            .userName(m.getUserName())
                            .time(m.getTime())
                            .build()
            );
        }
        return result;
    }
}
