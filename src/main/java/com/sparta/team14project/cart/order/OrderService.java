package com.sparta.team14project.cart.order;

import com.sparta.team14project.dto.OrderResponseDto;
import com.sparta.team14project.repository.*;
import com.sparta.team14project.dto.CartResponseDto;
import com.sparta.team14project.dto.OrderRequestDto;
import com.sparta.team14project.entity.*;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.core.Ordered;
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
    private final OrderedMenuRepository orderedMenuRepository;
    private final StoreRepository storeRepository;


    public CartResponseDto getCart(User user) {
        Cart cart = findCartById(user.getCart().getId());
        CartResponseDto responseDto = new CartResponseDto(cart);
        return responseDto;
    }




    @Transactional
    public CartResponseDto addMenu(Long menuId, User user) {
        Long cartId = user.getCart().getId();
        AddedMenu addedMenu = addedMenuRepository.findByCartIdAndMenuId(cartId, menuId);
        Menu menu = findMenuById(menuId);
        Cart cart = findCartById(cartId);
        if(cart.getStore()==null){
            cart.setStore(findStoreById(menu.getStore().getId()));
            if (addedMenu == null) {
                addedMenu = new AddedMenu(menu, cart);
                addedMenuRepository.save(addedMenu);
            }
            else {
                addedMenu.updateAddedMenu();
            }
            //cart에 store 저장
        } else if(cart.getStore().getId()==menu.getStore().getId()){
            if (addedMenu == null) {
                addedMenu = new AddedMenu(menu, cart);
                addedMenuRepository.save(addedMenu);
            }
            else {
                addedMenu.updateAddedMenu();
            }
        }
        else throw new IllegalArgumentException("다른 가게의 메뉴는 담을 수 없습니다.");
        CartResponseDto responseDto = new CartResponseDto(cart);
        return responseDto;
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
        if (user.getCart().getAddedMenuList().isEmpty()){
            user.getCart().setStore(null);
        }
        return new CartResponseDto(user.getCart());
    }

    @Transactional
    public OrderResponseDto orderMenu(OrderRequestDto requestDto, User user) {
        Cart cart = user.getCart();
        Delivery delivery = new Delivery(requestDto,user);

        int money = 0;
        for(AddedMenu am: cart.getAddedMenuList()){
            money+= am.getMenu().getPrice();
            OrderedMenu orderedMenu = new OrderedMenu(am,delivery);
            orderedMenuRepository.save(orderedMenu);
            delivery.addMenu(orderedMenu);

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
    private Store findStoreById(Long id) {
        return storeRepository.findById(id).orElseThrow(()->new NullPointerException("가게 정보를 찾을 수 없습니다."));
    }


    public CartResponseDto getOneCart(Long cartId, User user) {
        Cart cart = findCartById(cartId);
        return new CartResponseDto(cart);
    }
}
