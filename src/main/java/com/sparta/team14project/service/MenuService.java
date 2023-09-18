package com.sparta.team14project.service;

import com.sparta.team14project.dto.MenuRequestDto;
import com.sparta.team14project.dto.MenuResponseDto;
import com.sparta.team14project.entity.Menu;
import com.sparta.team14project.entity.Store;
import com.sparta.team14project.entity.User;
import com.sparta.team14project.entity.UserRoleEnum;
import com.sparta.team14project.repository.MenuRepository;
import com.sparta.team14project.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;

    public MenuResponseDto createMenu(MenuRequestDto requestDto, User user){
        Menu menu = new Menu();
        Store store = storeRepository.findStoreByUser(user);

        if(user.getUserRole().equals(UserRoleEnum.OWNER)){
            menu = menuRepository.save(new Menu(requestDto, store));
        }
        return new MenuResponseDto(menu);
    }

    // 메뉴 수정
    @Transactional
    public MenuResponseDto updateMenu(Long id, MenuRequestDto requestDto, User user) {
        Menu menu = findMenu(id);

        if(user.getUserRole().equals(UserRoleEnum.OWNER)){
            menu.update(requestDto);
            return new MenuResponseDto(menu);
        }
        else{
            throw new IllegalArgumentException("메뉴 수정 권한이 없습니다.");
        }
    }

    @Transactional
    public MenuResponseDto deleteBoard(Long id, User user) {
        Menu menu = findMenu(id);

        if(user.getUserRole().equals(UserRoleEnum.OWNER)){
            menuRepository.delete(menu);
            return new MenuResponseDto(menu);
        }
        else{
            throw new IllegalArgumentException("메뉴 삭제 실패하였습니다.");
        }
    }

    // 메뉴 찾기
    private Menu findMenu(Long id) {
        return menuRepository.findMenuById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 Board는 존재하지 않습니다.")
        );
    }
}
