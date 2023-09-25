package com.sparta.team14project.websocket;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {
    public enum MessageType{
        ORDER, DONE, ENTER
    }

    private MessageType type;
    private Long roomId;
    private String sender;
    private String message;
}