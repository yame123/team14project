package com.sparta.team14project.websocket;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@Getter
public class ChatRoom {
    private Long roomId;
    private String name;
    private Set<WebSocketSession> sessions = new HashSet<>();

    @Builder
    public ChatRoom(Long roomId, String name) {
        this.roomId = roomId;
        this.name = name;
    }

    public void handlerActions(WebSocketSession session, ChatMessage chatMessage, ChatService chatService) {
        if (chatMessage.getType().equals(ChatMessage.MessageType.ORDER)
                ||chatMessage.getType().equals(ChatMessage.MessageType.DONE)) {
            // chatMessage.setMessage(chatMessage.getSender() +"님이 주문했숑");

            sendMessage(chatMessage, chatService);
        }else {
            sessions.add(session);
            sendMessage(chatMessage, chatService);
        }
    }

    private <T> void sendMessage(T message, ChatService chatService) {
        Set<WebSocketSession> removesession = new HashSet<>();
        sessions.parallelStream()
                .forEach(session -> {
                    if (session.isOpen())chatService.sendMessage(session, message);
                    else removesession.add(session);
                });
        sessions.removeAll(removesession);
    }
}