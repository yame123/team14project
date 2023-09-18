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
    private final UserRepository userRepository;

    public CartResponseDto getCart(User user) {
        Cart cart = findCartById(user.getId());
        return new CartResponseDto(cart);
    }

    @Transactional
    public CartResponseDto addMenu(Long menuId, Long userId) {
        int showhiber = 1;
        System.out.println("$$$$$$$$$$$보여주기$$$$$$$$$   " + showhiber++);
        User user = findUserById(userId);
        System.out.println("$$$$$$$$$$$보여주기$$$$$$$$$   " + showhiber++);
        Cart cart = cartRepository.findCartByUser(user);
        Long cartId = cart.getId();
        System.out.println("$$$$$$$$$$$보여주기$$$$$$$$$   " + showhiber++);
        Menu menu = findMenuById(menuId);
        System.out.println("$$$$$$$$$$$보여주기$$$$$$$$$   " + showhiber++);
        AddedMenu addedMenu = addedMenuRepository.findByCartIdAndMenuId(cartId, menuId);
        System.out.println("$$$$$$$$$$$보여주기$$$$$$$$$   " + showhiber++);
        Store store = menu.getStore();
        cart.updateCart(store);

        if (addedMenu == null) {
            addedMenu = new AddedMenu(menu, cart);
            addedMenuRepository.save(addedMenu);
        }
        else {
            System.out.println("$$$$$$$$$$$보여주기$$$$$$$$$   " + showhiber++);
            addedMenu.updateAddedMenu();
        }
        System.out.println("$$$$$$$$$$$보여주기$$$$$$$$$   " + showhiber);
        return new CartResponseDto(cart);
    }

    @Transactional
    public CartResponseDto deleteMenu(Long menuId,User user) {
        Cart cart = cartRepository.findCartByUser(user);
        List<AddedMenu> addedMenuList = cart.getAddedMenuList();
        Menu menu = findMenuById(menuId);
        AddedMenu check = null;
        for(AddedMenu am:addedMenuList){
            if (am.getMenu().getId()==menu.getId()){
                check = am;
                break;
            }
        }
        if(check!=null) {
            addedMenuRepository.delete(check);
        } else throw new NullPointerException("장바구니에 없는 메뉴입니다.");
        return new CartResponseDto(cart);
    }

    @Transactional
    public OrderResponseDto orderMenu(OrderRequestDto requestDto, User user) {
        Cart cart = cartRepository.findCartByUser(user);
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

    private User findUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);
//        user.getDeliveryList().forEach(delivery -> delivery.getId());
        return user;
    }


}
