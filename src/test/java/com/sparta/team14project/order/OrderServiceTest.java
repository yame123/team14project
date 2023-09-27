package com.sparta.team14project.order;

import com.sparta.team14project.menu.dto.MenuRequestDto;
import com.sparta.team14project.menu.entity.Menu;
import com.sparta.team14project.menu.repository.MenuRepository;
import com.sparta.team14project.order.dto.CartResponseDto;
import com.sparta.team14project.order.dto.OrderRequestDto;
import com.sparta.team14project.order.dto.OrderResponseDto;
import com.sparta.team14project.order.entity.AddedMenu;
import com.sparta.team14project.order.entity.Cart;
import com.sparta.team14project.order.entity.Delivery;
import com.sparta.team14project.order.entity.OrderedMenu;
import com.sparta.team14project.order.repository.AddedMenuRepository;
import com.sparta.team14project.order.repository.CartRepository;
import com.sparta.team14project.order.repository.OrderRepository;
import com.sparta.team14project.order.repository.OrderedMenuRepository;
import com.sparta.team14project.store.dto.StoreRequestDto;
import com.sparta.team14project.store.entity.Store;
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

import static org.junit.jupiter.api.Assertions.*;
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
    public void testAddMenu() {
        User user = new User("username","password","email@email.email", UserRoleEnum.USER,1000000l);
        Cart cart = new Cart(user);
        StoreRequestDto storeRequestDto= new StoreRequestDto("storename","storedetails","storeaddress");
        // address name details
        Store store = new Store(storeRequestDto,user);
        MenuRequestDto menuRequestDto = new MenuRequestDto("menuname",1234,"menudetails");
        Menu menu = new Menu(menuRequestDto,store);
        AddedMenu addedMenu = new AddedMenu(menu,cart);
        Long allId = 1L;

        when(userRepository.findById(allId)).thenReturn(Optional.of(user));
        when(menuRepository.findById(allId)).thenReturn(Optional.of(menu));
        when(cartRepository.findCartByUser(user)).thenReturn(cart);
        when(addedMenuRepository.save(addedMenu)).thenReturn(new AddedMenu(menu,cart));

        CartResponseDto response = orderService.addMenu(allId, user);

        assertNotNull(response);
        System.out.println("response checked");
        assertNotNull(response.getAddedMenuList());
        System.out.println("addedmenulist checked");
        assertTrue(response.getAddedMenuList().get(0).getMenuResponseDto().getName().equals(menu.getName()));
        assertTrue(response.getAddedMenuList().get(0).getMenuResponseDto().getDetails().equals(menu.getDetails()));
        assertTrue(response.getAddedMenuList().get(0).getMenuResponseDto().getPrice()==menu.getPrice());
        System.out.println("addedmenu checked");
    }

    @Test
    public void testDeleteMenu() {
        User user = new User("username","password","email@email.email", UserRoleEnum.USER,1000000l);
        Cart cart = new Cart(user);
        StoreRequestDto storeRequestDto= new StoreRequestDto("storename","storedetails","storeaddress");
        // address name details
        Store store = new Store(storeRequestDto,user);
        MenuRequestDto menuRequestDto = new MenuRequestDto("menuname",1234,"menudetails");
        Menu menu = new Menu(menuRequestDto,store);
        AddedMenu addedMenu = new AddedMenu(menu,cart);
        cart.addMenu(addedMenu);

        Long allId = 1L;
        when(userRepository.findById(allId)).thenReturn(Optional.of(user));
        when(menuRepository.findById(allId)).thenReturn(Optional.of(menu));
        when(cartRepository.findCartByUser(user)).thenReturn(cart);
        when(addedMenuRepository.findAddedMenuByCartAndMenu(cart,menu)).thenReturn(Optional.of(addedMenu));
        CartResponseDto response = orderService.deleteMenu(allId, user);

        assertNotNull(response);
        System.out.println("response checked");
        assertTrue(response.getAddedMenuList().isEmpty());
        System.out.println("addedmenulist checked");
    }

    @Test
    public void testOrderMenu() {
        User user = new User("username","password","email@email.email", UserRoleEnum.USER,1000000l);
        Cart cart = new Cart(user);
        StoreRequestDto storeRequestDto= new StoreRequestDto("storename","storedetails","storeaddress");
        // address name details
        Store store = new Store(storeRequestDto,user);
        MenuRequestDto menuRequestDto = new MenuRequestDto("menuname",1234,"menudetails");
        Menu menu = new Menu(menuRequestDto,store);
        AddedMenu addedMenu = new AddedMenu(menu,cart);
        cart.addMenu(addedMenu);
        cart.updateStore(store);
        OrderRequestDto orderRequestDto = new OrderRequestDto();
        orderRequestDto.setAddress("주소주소");

        when(cartRepository.findCartByUser(any(User.class))).thenReturn(cart);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(orderedMenuRepository.saveAll(anyList())).thenReturn(Collections.emptyList());

        OrderResponseDto response = orderService.orderMenu(orderRequestDto, user);

        assertNotNull(response);
        assertEquals(response.getAddress(),"주소주소");
        assertEquals(response.getStorename(),"storename");
        assertEquals(response.getUsername(),"username");
        assertEquals(response.getOrderList().get(0).getCount(),addedMenu.getCount());
        assertEquals(response.getOrderList().get(0).getMenuResponseDto().getName(),addedMenu.getMenu().getName());
        assertEquals(response.getOrderList().get(0).getMenuResponseDto().getDetails(),addedMenu.getMenu().getDetails());
        assertEquals(response.getOrderList().get(0).getMenuResponseDto().getPrice(),addedMenu.getMenu().getPrice());
    }
}
