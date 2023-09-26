package com.sparta.team14project.order;

import com.sparta.team14project.menu.entity.Menu;
import com.sparta.team14project.menu.repository.MenuRepository;
import com.sparta.team14project.order.dto.CartResponseDto;
import com.sparta.team14project.order.dto.OrderRequestDto;
import com.sparta.team14project.order.dto.OrderResponseDto;
import com.sparta.team14project.order.entity.Cart;
import com.sparta.team14project.order.entity.Delivery;
import com.sparta.team14project.order.repository.AddedMenuRepository;
import com.sparta.team14project.order.repository.CartRepository;
import com.sparta.team14project.order.repository.OrderRepository;
import com.sparta.team14project.order.repository.OrderedMenuRepository;
import com.sparta.team14project.user.entity.User;
import com.sparta.team14project.user.entity.UserRoleEnum;
import com.sparta.team14project.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    @InjectMocks
    OrderService orderService;

    @Mock
    OrderRepository orderRepository;

    @Mock
    MenuRepository menuRepository;

    @Mock
    AddedMenuRepository addedMenuRepository;

    @Mock
    CartRepository cartRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    OrderedMenuRepository orderedMenuRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetCart() {
        User user = new User("username","password","email@email.email", UserRoleEnum.USER,1000000l);
        Cart cart = new Cart(user);
        when(cartRepository.findCartByUser(user)).thenReturn(cart);

        CartResponseDto response = orderService.getCart(user);

        assertNotNull(response);
        assertEquals(user.getUsername(),response.getUsername(),()->"몰?루");
        System.out.println("username checked");
        assertEquals(cart.getAddedMenuList(), response.getAddedMenuList());
        System.out.println("addedmenulist checked");
    }

    @Test
    public void testAddMenu() {
        User user = new User("username","password","email@email.email", UserRoleEnum.USER,1000000l);
        Cart cart = new Cart(user);
        Long menuId = 1L;
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(menuRepository.findById(menuId)).thenReturn(Optional.of(new Menu()));
        when(cartRepository.findCartByUser(user)).thenReturn(new Cart(user));

        CartResponseDto response = orderService.addMenu(menuId, user);

        assertNotNull(response);
        System.out.println("response checked");
        assertNotNull(response.getAddedMenuList());
        System.out.println("addedmenulist checked");
        assertNotNull(response.getAddedMenuList().contains(menuRepository.findById(menuId)));
        System.out.println("addedmenu checked");
    }

//    @Test
//    public void testDeleteMenu() {
//        User user = new User("username","password","email@email.email", UserRoleEnum.USER,1000000l);
//
//        Long menuId = 1L;
//        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
//        when(menuRepository.findById(menuId)).thenReturn(Optional.of(new Menu()));
//        when(cartRepository.findCartByUser(user)).thenReturn(new Cart(user));
//
//        CartResponseDto response = orderService.deleteMenu(menuId, user);
//
//        assertNotNull(response);
//        System.out.println("response checked");
//        assertNotNull(response.getAddedMenuList().contains(menuRepository.findById(menuId)));
//        System.out.println("addedmenu checked");
//    }
//
//    @Test
//    public void testOrderMenu() {
//        User user = new User("username","password","email@email.email", UserRoleEnum.USER,1000000l);
//        Cart cart = new Cart(user);
//        when(cartRepository.findCartByUser(user)).thenReturn(cart);
//        when(orderRepository.save(any(Delivery.class))).thenReturn(new Delivery());
//        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
//
//        when(orderedMenuRepository.saveAll(anyList())).thenReturn(Collections.emptyList());
//
//        OrderRequestDto requestDto = new OrderRequestDto();
//        requestDto.setAddress("address");
//
//
//        OrderResponseDto response = orderService.orderMenu(requestDto, user);
//
//        assertNotNull(response);
//    }
}
