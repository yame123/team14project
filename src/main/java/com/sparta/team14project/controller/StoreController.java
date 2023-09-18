package com.sparta.team14project.controller;

import com.sparta.team14project.dto.MessageResponseDto;
import com.sparta.team14project.dto.StoreRequestDto;
import com.sparta.team14project.dto.StoreResponseDto;
import com.sparta.team14project.security.UserDetailsImpl;
import com.sparta.team14project.service.StoreService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StoreController {
    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @PostMapping("/store/create")
    public StoreResponseDto createStore(@RequestBody StoreRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
            return storeService.createStore(requestDto, userDetails);
    }

    @GetMapping("/store")
    public List<StoreResponseDto> getStores() {
        return storeService.getStores();
    }

    @GetMapping("/store/{keyword}")
    public List<StoreResponseDto> getStoresByKeyword(@RequestParam String keyword){
        return storeService.getStoreByKeyword(keyword);
    }

    @PutMapping("/store/{storeId}")
    public StoreResponseDto updateStore(@PathVariable("storeId") Long storeId,
                                        @RequestBody StoreRequestDto requestDto,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails){
        return storeService.updateStore(storeId, requestDto, userDetails);
    }

    @DeleteMapping("/store/{storeId}")
    public MessageResponseDto deleteStore(@PathVariable("storeId") Long storeId,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails){
        return storeService.deleteStore(storeId, userDetails);
    }

    @PutMapping("/delivery/{orderId}")
    public MessageResponseDto deliveryDone(@PathVariable Long orderId) {
        return storeService.deliveryDone(orderId);
    }


}
