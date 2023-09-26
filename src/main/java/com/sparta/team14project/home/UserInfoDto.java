package com.sparta.team14project.home;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfoDto {
    Long userid;
    String username;
    boolean isAdmin;
}