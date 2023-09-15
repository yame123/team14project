package com.sparta.team14project.service;

import com.sparta.team14project.dto.MessageResponseDto;
import com.sparta.team14project.dto.StoreRequestDto;
import com.sparta.team14project.dto.StoreResponseDto;
import com.sparta.team14project.repository.StoreRepository;
import com.sparta.team14project.security.UserDetailsImpl;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.sparta.team14project.entity.Store;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StoreService {

    private final StoreRepository storeRepository;

    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public StoreResponseDto createStore(StoreRequestDto requestDto, UserDetailsImpl userDetails) {
        if(userDetails.getUser().getUserRole().getAuthority().equals("ROLE_OWNER")){ // Owner인증을 AOP로
            Store store = new Store(requestDto);
            Store saveStore = storeRepository.save(store);
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
        if(userDetails.getUser().getUserRole().getAuthority().equals("ROLE_OWNER")){
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
                    "업장 삭제가 완료되었습니다.", HttpStatus.OK
            );
            return messageResponseDto;
        } else{
            MessageResponseDto messageResponseDto = new MessageResponseDto(
                    "삭제 권한이 없습니다.", HttpStatus.BAD_REQUEST
            );
            return messageResponseDto;
        }
    }
}
