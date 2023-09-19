package com.sparta.team14project.order;

import com.sparta.team14project.order.dto.CartResponseDto;
import com.sparta.team14project.order.dto.OrderRequestDto;
import com.sparta.team14project.order.dto.OrderResponseDto;
import com.sparta.team14project.menu.entity.Menu;
import com.sparta.team14project.menu.repository.MenuRepository;
import com.sparta.team14project.order.entity.AddedMenu;
import com.sparta.team14project.order.entity.Cart;
import com.sparta.team14project.order.entity.Delivery;
import com.sparta.team14project.order.entity.OrderedMenu;
import com.sparta.team14project.order.repository.AddedMenuRepository;
import com.sparta.team14project.order.repository.CartRepository;
import com.sparta.team14project.order.repository.OrderRepository;
import com.sparta.team14project.order.repository.OrderedMenuRepository;
import com.sparta.team14project.store.entity.Store;
import com.sparta.team14project.user.entity.User;
import com.sparta.team14project.user.repository.UserRepository;
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
    private final OrderedMenuRepository orderedMenuRepository;


    public CartResponseDto getCart(User user) {
        Cart cart = findCartByUser(user);
        return new CartResponseDto(cart);
    }

    @Transactional // 에디드매뉴 부르고 저장하는게 아니고 변경만 하는 코드가 있어서 지우면 안됨
    public CartResponseDto addMenu(Long menuId, User user) {
        // User user = findUserById(user);
        Cart cart = findCartByUser(user);
        Menu menu = findMenuById(menuId);
        Store store = menu.getStore();

        if(cart.getAddedMenuList().isEmpty()){
            cart.updateStore(store);
        } // 카트가 비어있다면 메뉴를 넣기 전에 카트의 store를 메뉴의 store로 변경
        if(cart.getStore().getId() == menu.getStore().getId()){ // 카트의 store와 menu의 store이 같은지 확인
            //메뉴를 카트에 담을 수 있는지 확인 후에 생성하기 위해 if문 안에 배치
            AddedMenu addedMenu = findAddedMenuByCartAndMenu(cart, menu);
            if (addedMenu == null) {
                addedMenu = new AddedMenu(menu, cart);
                addedMenuRepository.save(addedMenu);
                cart.addMenu(addedMenu); // CartResponseDto에 적용하기 위해 별도로 추가
            } // 카트 안에 고른 메뉴가 없다면 새로 생성 후 추가
            else {
                addedMenu.updateAddedMenu(); // 이미 카트에 들어있는 메뉴의 값을 수정하기에 추가하지 않아도 CRD에 반영됨
            } // 카트 안에 고른 메뉴가 있다면 해당 메뉴의 갯수를 1 증가
        } else throw new IllegalArgumentException("같은 매장의 메뉴만 담을 수 있습니다.");
        return new CartResponseDto(cart); // 담은 후 정상적으로 담겼는지 확인을 할 수 있게 카트를 반환
    }

    @Transactional
    public CartResponseDto deleteMenu(Long menuId,User user) {
        Cart cart = findCartByUser(user);
        Menu menu = findMenuById(menuId);

        AddedMenu addedMenu = findAddedMenuByCartAndMenu(cart,menu);

        if (addedMenu.getCount() == 1){ // 담긴 메뉴의 갯수가 1개면 담긴 메뉴 삭제
            cart.removeMenu(addedMenu); // CartResponseDto에 적용하기 위해 별도로 추가
            addedMenuRepository.delete(addedMenu);
        }else { // 2개 이상이면 갯수를 1 감소
            addedMenu.delupdateAddedMenu(); // 이미 카트에 들어있는 메뉴의 값을 수정만 하기에 제거과정이 필요없음
        } // 메뉴가 store와 다르다면 담을 수 없기에 뺄 땐 굳이 store와 비교하지 않아도 됨
        if(cart.getAddedMenuList().isEmpty()){ // 삭제 후 담긴 메뉴가 없다면 store 초기화
            cart.resetStore();
        }
        return new CartResponseDto(cart);
    }

    @Transactional
    public OrderResponseDto orderMenu(OrderRequestDto requestDto, User user) {
        // User user = findUserById(id);
        Cart cart = findCartByUser(user);
        Store store = cart.getStore();

        if(cart.getAddedMenuList().isEmpty()){
            throw new IllegalArgumentException("메뉴가 들어있지 않습니다.");
        }

        Delivery delivery = new Delivery(requestDto,user,store);
        int money = 0;

        for(AddedMenu am: cart.getAddedMenuList()){
            money+= am.getMenu().getPrice() * am.getCount(); // 메뉴 가격 * 갯수로 지불금액 합산
            OrderedMenu orderedMenu = new OrderedMenu(am,delivery); // 담긴 메뉴를 주문할 메뉴로 변경
            delivery.addMenu(orderedMenu); // 주문 정보에 주문 메뉴 추가
        }
        if (user.getUserPoint()<money) throw new IllegalArgumentException("잔액이 부족합니다.");

        delivery.payAndSetPrice(money); // 유저에게 지불 청구와 money 세팅을 동시에
        addedMenuRepository.deleteAll(cart.getAddedMenuList()); // 카트에 담긴 메뉴 전부 삭제
        orderedMenuRepository.saveAll(delivery.getOrderedMenuList());// 주문메뉴 저장
        Delivery savedDelivery = orderRepository.save(delivery);
        // 주문 정보 저장 및 출력용 변수 생성(안하고 delivery 그대로 출력하는 거랑 상관이 있나
        return new OrderResponseDto(savedDelivery);
    }

    private Menu findMenuById(Long id){
        Menu menu = menuRepository.findById(id).orElseThrow(()->new NullPointerException("메뉴 정보를 찾을 수 없습니다."));
        return menu;
    }
    private Cart findCartByUser(User user) {
        return cartRepository.findCartByUser(user);
    }

    private AddedMenu findAddedMenuByCartAndMenu(Cart cart, Menu menu) {
        return addedMenuRepository.findAddedMenuByCartAndMenu(cart,menu);
    }


}
