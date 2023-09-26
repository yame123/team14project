package com.sparta.team14project.store;

import com.sparta.team14project.message.MessageResponseDto;
import com.sparta.team14project.order.dto.OrderResponseDto;
import com.sparta.team14project.store.dto.*;
import com.sparta.team14project.user.login.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;

    @PostMapping("/store/create")
    public StoreResponseDto createStore(@RequestBody StoreRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
            return storeService.createStore(requestDto, userDetails);
    }

    @GetMapping("/store/{storeId}")
    public StoreMenuResponseDto getStore(@PathVariable("storeId") Long storeId){
        return storeService.getStore(storeId);
    }

    @GetMapping("/store")
    public List<CookieStoreResponseDto> getStores() {
        return storeService.getStores();
    }

    @GetMapping("/store-rank")
    public List<CookieStoreResponseDto> getStoresRank() {
        return storeService.getStoresRank();
    }

    @GetMapping("/store-info/{storeId}")
    public Object getStoreInfo(Long storeId) {
        return storeService.getStoreInfo(Long.toString(storeId));
    }

    @GetMapping("/store-search")
    public List<StoreResponseDto> getStoresByKeyword(@RequestParam String keyword){
        return storeService.getStoreByKeyword(keyword);
    }

    @DeleteMapping("/delete-caches")
    public ResponseEntity<String> clearAllStoreCaches() {
        storeService.clearAllStoreCaches();
        return ResponseEntity.ok("All cache cleared.");
    }

    @GetMapping("/store/getStoreId")
    public Long getStoreId(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return storeService.getStoreId(userDetails);
    }

    @PutMapping("/store/{storeId}")
    public StoreResponseDto updateStore(@PathVariable("storeId") Long storeId,
                                        @RequestBody StoreRequestDto requestDto){
        return storeService.updateStore(storeId, requestDto);
    }

    @DeleteMapping("/store/{storeId}")
    public MessageResponseDto deleteStore(@PathVariable("storeId") Long storeId){
        return storeService.deleteStore(storeId);
    }

    @PutMapping("/store/order/{orderId}")
    public MessageResponseDto deliveryDone(@PathVariable Long orderId,@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return storeService.deliveryDone(orderId, userDetails.getUser());
    }

    @GetMapping("/store/order")
    public List<OrderResponseDto> deliveryCheckTest(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return storeService.deliveryCheck(userDetails.getUser());
    }

}
