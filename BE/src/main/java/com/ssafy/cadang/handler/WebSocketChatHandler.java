package com.ssafy.cadang.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.cadang.dto.ChatMessageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketChatHandler extends TextWebSocketHandler {
    private final ObjectMapper mapper;
    private final Set<WebSocketSession> sessions = new HashSet<>();

    // chatRoomId
    private final Map<Long,Set<WebSocketSession>> chatRoomSessionMap = new HashMap<>();

    // 소켓 연결 확인
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // TODO Auto-generated method stub
        log.info("{} 연결됨", session.getId());
        sessions.add(session);
    }

    // 소켓 통신 시 메세지의 전송을 다루는 부분
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload {}", payload);

        // 페이로드 -> chatMessage로 변환
        ChatMessageDTO chatMessageDTO = mapper.readValue(payload, ChatMessageDTO.class);
        log.info("session {}", chatMessageDTO.toString());

        Long chatRoomId = chatMessageDTO.getChatRoomId();
        // 메모리 상에 채팅방에 대한 세션 없으면 만들어줌
        if(!chatRoomSessionMap.containsKey(chatRoomId)){
            chatRoomSessionMap.put(chatRoomId,new HashSet<>());
        }
        Set<WebSocketSession> chatRoomSession = chatRoomSessionMap.get(chatRoomId);

        // message 에 담긴 타입을 확인한다.
        // 이때 message 에서 getType 으로 가져온 내용이
        // ChatDTO 의 열거형인 MessageType 안에 있는 ENTER 과 동일한 값이라면
        if (chatMessageDTO.getMessageType().equals(ChatMessageDTO.MessageType.ENTER)) {
            // sessions 에 넘어온 session 을 담고,
            chatRoomSession.add(session);
        }
        else if (chatMessageDTO.getMessageType().equals(ChatMessageDTO.MessageType.QUIT)) {
            //afterConnectionClosed(session);
            chatRoomSession.remove(session);
        }
        else
            sendMessageToChatRoom(chatMessageDTO, chatRoomSession);

    }

    // 소켓 종료 확인
    public void afterConnectionClosed(WebSocketSession session) throws Exception {
        // TODO Auto-generated method stub
        log.info("{} 연결 끊김", session.getId());
        sessions.remove(session);
    }

    // ====== 채팅 관련 메소드 ======
    private void removeClosedSession(Set<WebSocketSession> chatRoomSession) {
        chatRoomSession.removeIf(sess -> !sessions.contains(sess));
    }

    //병렬로 채팅방에 있는 모든 사용자 send
    private void sendMessageToChatRoom(ChatMessageDTO chatMessageDTO, Set<WebSocketSession> chatRoomSession) {
        //user Name add
        //ChatMessageDTO userName
        //chatMessageDTO.setSenderId();

        chatRoomSession.parallelStream().forEach(sess -> sendMessage(sess, chatMessageDTO));

        //service chat room record

    }


    public <T> void sendMessage(WebSocketSession session, T message) {
        try{
            session.sendMessage(new TextMessage(mapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}