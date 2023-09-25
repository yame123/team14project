package com.sparta.team14project.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {
    private final ObjectMapper objectMapper;
    private Map<Long, ChatRoom> chatRooms;

    @PostConstruct
    private void init() {
        chatRooms = new LinkedHashMap<>();
    }

    public List<ChatRoom> findAllRoom() {
        return new ArrayList<>(chatRooms.values());
    }

    public ChatRoom findRoomById(Long roomId) {
        return chatRooms.get(roomId);
    }

    public ChatRoom createRoom(ChatRoomRequestDto requestDto, String username) {
        Long id = requestDto.getId();
        if(chatRooms.containsKey(id)){
            return chatRooms.get(id);
        }
        ChatRoom chatRoom = ChatRoom.builder()
                .roomId(id)
                .name(username)
                .build();
        chatRooms.put(id, chatRoom);
        return chatRoom;
    }

    public void removeRoom(Long roomId){
        chatRooms.remove(roomId);
    }

    public <T> void sendMessage(WebSocketSession session, T message) {
        try{
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}