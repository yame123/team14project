package com.sparta.team14project.cart.order;

import com.sparta.team14project.dto.OrderResponseDto;
import com.sparta.team14project.repository.*;
import com.sparta.team14project.dto.CartResponseDto;
import com.sparta.team14project.dto.OrderRequestDto;
import com.sparta.team14project.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;
    private final AddedMenuRepository addedMenuRepository;
    private final CartRepository cartRepository;

    public CartResponseDto getCart(User user) {
        Cart cart = findCartById(user.getId());
        return new CartResponseDto(cart);
    }




    @Transactional
    public CartResponseDto addMenu(Long menuId, User user) {

        List<AddedMenu> addedMenuList = user.getCart().getAddedMenuList();
        Menu menu = findMenuById(menuId);
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
        Menu menu = findMenuById(menuId);
        AddedMenu check = null;
        for(AddedMenu am:addedMenuList){
            if (am.getMenu().getId()==menu.getId()){
                check = am;
                break;
            }
        }
        if(check!=null) {
            if (check.getCount() == 1) {
                addedMenuRepository.delete(check);
            } else {
                check.setCount(check.getCount() - 1);
            }
        } else throw new NullPointerException("장바구니에 없는 메뉴입니다.");
        return new CartResponseDto(user.getCart());
    }

    @Transactional
    public OrderResponseDto orderMenu(OrderRequestDto requestDto, User user) {
        Cart cart = user.getCart();
        Delivery delivery = new Delivery();
        int money = 0;
        for(AddedMenu am: cart.getAddedMenuList()){
            money+= am.getMenu().getPrice();
            delivery.addMenu(new OrderedMenu(am));
        }
        if (user.getUserPoint()<money) throw new IllegalArgumentException("잔액이 부족합니다.");
        user.pay(money);
        cart.resetCart();
        Delivery savedDelivery = orderRepository.save(delivery);
        return new OrderResponseDto(savedDelivery);
    }

    private Menu findMenuById(Long id){
        Menu menu = menuRepository.findById(id).orElseThrow(()->new NullPointerException("메뉴 정보를 찾을 수 없습니다."));
        return menu;
    }
    private Cart findCartById(Long id) {
        return cartRepository.findById(id).orElseThrow(()->new NullPointerException("카트 정보를 찾을 수 없습니다."));
    }


}