package com.sparta.team14project.service;

import com.sparta.team14project.dto.MessageResponseDto;
import com.sparta.team14project.dto.StoreRequestDto;
import com.sparta.team14project.dto.StoreResponseDto;
import com.sparta.team14project.entity.User;
import com.sparta.team14project.repository.StoreRepository;
import com.sparta.team14project.repository.UserRepository;
import com.sparta.team14project.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.sparta.team14project.entity.Store;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    public StoreResponseDto createStore(StoreRequestDto requestDto, UserDetailsImpl userDetails) {
        if(userDetails.getUser().getUserRole().getAuthority().equals("ROLE_OWNER")){
            // user 정보 userDetails에서 추출
            User user = userDetails.getUser();
            // requestDto 정보를 저장
            Store store = new Store(requestDto, user);

            // store 정보를 repository에 저장
            Store saveStore = storeRepository.save(store);

            // store 정보를 DTO에 넣어 반환
            StoreResponseDto storeResponseDto = new StoreResponseDto(saveStore);
            return storeResponseDto;
        } else{
            throw new IllegalArgumentException("수정 권한이 없습니다.");
        }
    }

    public List<StoreResponseDto> getStores() {
        return storeRepository.findAll().stream().map(StoreResponseDto::new).toList();
    }

    public List<StoreResponseDto> getStoreByKeyword(String keyword) {
        return storeRepository.findAllByStoreNameContains(keyword).stream().map(StoreResponseDto::new).toList();
    }

    @Transactional
    public StoreResponseDto updateStore(Long id, StoreRequestDto requestDto, UserDetailsImpl userDetails) {
        if(userDetails.getUser().getUserRole().getAuthority().equals("ROLE_OWNER") ){
            Store store = findStore(id);
            store.update(requestDto);
            return new StoreResponseDto(store);
        } else{
            throw new IllegalArgumentException("수정 권한이 없습니다.");
        }

    }

    private Store findStore(Long id) {
        return storeRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("데이터가 없습니다.")
        );
    }

    public MessageResponseDto deleteStore(Long id, UserDetailsImpl userDetails) {
        if(userDetails.getUser().getUserRole().getAuthority().equals("ROLE_OWNER")){ // Owner인증을 AOP로
            Store store = findStore(id);
            storeRepository.delete(store);
            MessageResponseDto messageResponseDto = new MessageResponseDto(
                    "업장 삭제가 완료되었습니다.", 200
            );
            return messageResponseDto;
        } else{
            MessageResponseDto messageResponseDto = new MessageResponseDto(
                    "삭제 권한이 없습니다.", 400
            );
            return messageResponseDto;
        }
    }
}
