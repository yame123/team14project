package com.sparta.team14project.cart.order;

import com.sparta.team14project.dto.OrderResponseDto;
import com.sparta.team14project.repository.AddedMenuRepository;
import com.sparta.team14project.repository.OrderedMenuRepository;
import com.sparta.team14project.dto.CartResponseDto;
import com.sparta.team14project.dto.OrderRequestDto;
import com.sparta.team14project.entity.*;
import com.sparta.team14project.repository.OrderRepository;
import com.sparta.team14project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final MenuRepository menuRepository;
    private final AddedMenuRepository addedMenuRepository;
    public CartResponseDto getCart(User user) {
        return new CartResponseDto(user.getCart());
    }


    @Transactional
    public CartResponseDto addMenu(Long menuId, User user) {
        List<AddedMenu> addedMenuList = user.getCart().getAddedMenuList();
        Menu menu = findById(menuId);
        AddedMenu check = null;
        for(AddedMenu am:addedMenuList){
            if (am.getMenu().getId()==menu.getId()){
                check = am;
                break;
            }
        }
        if(check!=null) check.setCount(check.getCount()+1);
        else addedMenuList.add(new AddedMenu(menu,1));
        return new CartResponseDto(user.getCart());
    }

    @Transactional
    public CartResponseDto deleteMenu(Long menuId,User user) {
        List<AddedMenu> addedMenuList = user.getCart().getAddedMenuList();
        Menu menu = menuRepository.findById(menuId);
        AddedMenu check = null;
        for(AddedMenu am:addedMenuList){
            if (am.getMenu().getId()==menu.getId()){
                check = am;
                break;
            }
        }
        if(check!=null) check.getCount()==1? addedMenuRepository.delete(check):check.setCount(check.getCount()-1);
        else throw new NullPointerException("장바구니에 없는 메뉴입니다.");
        return new CartResponseDto(user.getCart());
    }

    @Transactional
    public OrderResponseDto orderMenu(OrderRequestDto requestDto, User user) {
        Cart cart = user.getCart();
        Order order = new Order();
        Long money = 0;
        for(AddedMenu am: cart.getAddedMenuList()){
            money+= am.getMenu().getMenuPrice;
            order.addMenu(new OrderedMenu(am));
        }
        if (user.getUserPoint()<money) throw new IllegalArgumentException("잔액이 부족합니다.");
        user.pay(money);
        cart.resetCart();
        Order savedOrder = orderRepository.save(order);
        return new OrderResponseDto(savedOrder);
    }
}
