package com.sparta.team14project.menu;

import com.sparta.team14project.menu.dto.MenuRequestDto;
import com.sparta.team14project.menu.dto.MenuResponseDto;
import com.sparta.team14project.user.login.security.UserDetailsImpl;
import com.sparta.team14project.menu.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MenuController {
    private final MenuService menuService;

    // 메뉴 등록
    @PostMapping("/menu")
    public MenuResponseDto createMenu(@RequestBody MenuRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return menuService.createMenu(requestDto, userDetails.getUser());
    }

    // 메뉴 수정
    @PutMapping("/menu/{id}")       // menuId
    public MenuResponseDto updateMenu(@PathVariable Long id,
                                      @RequestBody MenuRequestDto requestDto){
        return menuService.updateMenu(id, requestDto);
    }

    // 메뉴 삭제
    @DeleteMapping("/menu/{id}")
    public MenuResponseDto deleteMenu(@PathVariable Long id) {
        return menuService.deleteBoard(id);
    }
}
