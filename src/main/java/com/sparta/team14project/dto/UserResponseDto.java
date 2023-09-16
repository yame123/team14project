package com.sparta.team14project.dto;

import com.sparta.team14project.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponseDto {
    private String username;
    private String email;
    private Enum userRole;
    private Long userPoint;

    public UserResponseDto(User user){
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.userRole = user.getUserRole();
        this.userPoint = user.getUserPoint();
    }

}
