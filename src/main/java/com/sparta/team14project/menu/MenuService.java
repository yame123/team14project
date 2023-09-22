package com.sparta.team14project.menu;

import com.sparta.team14project.menu.dto.MenuRequestDto;
import com.sparta.team14project.menu.dto.MenuResponseDto;
import com.sparta.team14project.menu.entity.Menu;
import com.sparta.team14project.menu.repository.MenuRepository;
import com.sparta.team14project.store.entity.Store;
import com.sparta.team14project.store.repository.StoreRepository;
import com.sparta.team14project.user.entity.User;
import com.sparta.team14project.user.entity.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;

    public MenuResponseDto createMenu(MenuRequestDto requestDto, User user) {
        Store store = storeRepository.findStoreByUser(user);
        Menu menu = menuRepository.save(new Menu(requestDto, store));
        return new MenuResponseDto(menu);
    }

    // 메뉴 수정
    @Transactional
    public MenuResponseDto updateMenu(Long id, MenuRequestDto requestDto) {
        Menu menu = findMenu(id);
        menu.update(requestDto);
        return new MenuResponseDto(menu);
    }

    public MenuResponseDto deleteBoard(Long id) {
        Menu menu = findMenu(id);
        menuRepository.delete(menu);
        return new MenuResponseDto(menu);
    }

    // 메뉴 찾기
    private Menu findMenu(Long id) {
        return menuRepository.findMenuById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 Board는 존재하지 않습니다.")
        );
    }

    public MenuResponseDto showMenuDetail(Long id) {
        Menu menu = findMenu(id);
        return new MenuResponseDto(menu);
    }
}
