package com.sparta.team14project.controller;

import com.sparta.team14project.dto.MenuRequestDto;
import com.sparta.team14project.dto.MenuResponseDto;
import com.sparta.team14project.entity.Menu;
import com.sparta.team14project.repository.MenuRepository;
import com.sparta.team14project.security.UserDetailsImpl;
import com.sparta.team14project.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/store")
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
                                      @RequestBody MenuRequestDto requestDto,
                                      @AuthenticationPrincipal UserDetailsImpl userDetails){
        return menuService.updateMenu(id, requestDto, userDetails.getUser());
    }

    // 메뉴 삭제
    @DeleteMapping("/menu/{id}")
    public MenuResponseDto deleteBoard(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return menuService.deleteBoard(id, userDetails.getUser());
    }
}
