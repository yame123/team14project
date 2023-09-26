package com.sparta.team14project.websocket;

import com.sparta.team14project.user.login.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;

    @PostMapping
    public ChatRoom createRoom(@RequestBody ChatRoomRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return chatService.createRoom(requestDto,userDetails.getUser().getUsername());
    }

    @GetMapping
    public List<ChatRoom> findAllRoom() {
        return chatService.findAllRoom();
    }

    @DeleteMapping
    public void removeRoom(@RequestBody Long id) {
        chatService.removeRoom(id);
    }
}