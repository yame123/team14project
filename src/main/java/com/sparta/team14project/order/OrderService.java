package com.sparta.team14project.order;

import com.sparta.team14project.dto.CartResponseDto;
import com.sparta.team14project.dto.OrderRequestDto;
import com.sparta.team14project.dto.OrderResponseDto;
import com.sparta.team14project.entity.*;
import com.sparta.team14project.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;
    private final AddedMenuRepository addedMenuRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final OrderedMenuRepository orderedMenuRepository;


    public CartResponseDto getCart(User user) {
        Cart cart = findCartByUser(user);
        return new CartResponseDto(cart);
    }

    @Transactional
    public CartResponseDto addMenu(Long menuId, Long userId) {
        User user = findUserById(userId);
        Cart cart = findCartByUser(user);
        Menu menu = findMenuById(menuId);
        AddedMenu addedMenu = findAddedMenuByCartAndMenu(cart, menu);
        Store store = menu.getStore();
        cart.updateCart(store);
        if (addedMenu == null) {
            addedMenu = new AddedMenu(menu, cart);
            addedMenuRepository.save(addedMenu);
        }
        else {
            addedMenu.updateAddedMenu();
        }
        return new CartResponseDto(cart);
    }

    @Transactional
    public CartResponseDto deleteMenu(Long menuId,User user) {
        Cart cart = findCartByUser(user);
        Menu menu = findMenuById(menuId);
        AddedMenu addedMenu = findAddedMenuByCartAndMenu(cart,menu);
        if (addedMenu.getCount() == 1){
            addedMenuRepository.delete(addedMenu);
        }else {
            addedMenu.delupdateAddedMenu();
        }
        return new CartResponseDto(cart);
    }

    @Transactional
    public OrderResponseDto orderMenu(OrderRequestDto requestDto, Long userId) {
        User user = findUserById(userId);
        Cart cart = findCartByUser(user);
        Store store = cart.getStore();
        Delivery delivery = new Delivery(requestDto, user, store);
        int money = 0;
        for(AddedMenu am: cart.getAddedMenuList()){
            money += am.getMenu().getPrice() * am.getCount();
            OrderedMenu orderedMenu = new OrderedMenu(am,delivery);
            delivery.addMenu(orderedMenu);
        }
        if (user.getUserPoint() < money) throw new IllegalArgumentException("잔액이 부족합니다.");
        orderedMenuRepository.saveAll(delivery.getOrderedMenuList());//계산하고 집어넣기
        user.pay(money);
//        addedMenuRepository.deleteAll(cart.getAddedMenuList());

        Delivery savedDelivery = orderRepository.save(delivery);
        return new OrderResponseDto(savedDelivery);
    }

    private Menu findMenuById(Long id){
        Menu menu = menuRepository.findById(id).orElseThrow(()->new NullPointerException("메뉴 정보를 찾을 수 없습니다."));
        return menu;
    }
    private Cart findCartByUser(User user) {
        return cartRepository.findCartByUser(user);
    }


    private User findUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        user.getDeliveryList().forEach(delivery -> delivery.getId());
        return user;
    }

    private AddedMenu findAddedMenuByCartAndMenu(Cart cart, Menu menu) {
        return addedMenuRepository.findAddedMenuByCartAndMenu(cart,menu);
//        user.getDeliveryList().forEach(delivery -> delivery.getId());
    }


}