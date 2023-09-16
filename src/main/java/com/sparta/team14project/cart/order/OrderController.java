package com.sparta.team14project.cart.order;

import com.sparta.team14project.dto.CartResponseDto;
import com.sparta.team14project.dto.OrderRequestDto;
import com.sparta.team14project.dto.OrderResponseDto;
import com.sparta.team14project.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

//카트추가도 여기 담을겁니다

@RestController
@RequestMapping({"/api"})
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/cart")
    public CartResponseDto getCart(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return orderService.getCart(userDetails.getUser());
    }//유저 정보 받는 방식 입력

    @PostMapping("/cart/{menuId}")
    public CartResponseDto addMenu(@PathVariable Long menuId,@AuthenticationPrincipal UserDetailsImpl userDetails){
        return orderService.addMenu(menuId,userDetails.getUser());
    }//유저 정보 받는 방식 입력

    @DeleteMapping("/cart/{menuId}")
    public CartResponseDto deleteMenu(@PathVariable Long menuId,@AuthenticationPrincipal UserDetailsImpl userDetails){
        return orderService.deleteMenu(menuId,userDetails.getUser());
    }

    @PostMapping("/order")
    public OrderResponseDto orderMenu(@RequestBody OrderRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return orderService.orderMenu(requestDto,userDetails.getUser());
    }
}
