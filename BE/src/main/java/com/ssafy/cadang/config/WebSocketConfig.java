package com.ssafy.cadang.config;

import com.ssafy.cadang.handler.WebSocketChatHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@RequiredArgsConstructor
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final WebSocketChatHandler webSocketChatHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // setAllowedOrigins("*")는 모든 ip에서 접속 가능하도록 해줌
        // ws://주소:포트/ws/chat
        registry.addHandler(webSocketChatHandler, "/ws/chat")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }
}