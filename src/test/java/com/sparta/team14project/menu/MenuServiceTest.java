package com.sparta.team14project.menu;

import com.sparta.team14project.menu.dto.MenuRequestDto;
import com.sparta.team14project.menu.dto.MenuResponseDto;
import com.sparta.team14project.menu.entity.Menu;
import com.sparta.team14project.menu.repository.MenuRepository;
import com.sparta.team14project.store.dto.StoreRequestDto;
import com.sparta.team14project.store.entity.Store;
import com.sparta.team14project.store.repository.StoreRepository;
import com.sparta.team14project.user.entity.User;
import com.sparta.team14project.user.entity.UserRoleEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MenuServiceTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private MenuRepository menuRepository;


    @Test
    public void testCreateMenu() {
        // 테스트용 Store 객체 생성 및 저장
        User user = createOwner1(); // User 객체를 적절히 생성해야 합니다.
        entityManager.persist(user);
        entityManager.flush();

        StoreRequestDto storeRequestDto = new StoreRequestDto("가게1", "설명1", "주소1");
        Store store = new Store(storeRequestDto, user);
        entityManager.persist(store);
        entityManager.flush();

        // 테스트용 MenuRequestDto 생성
        MenuRequestDto menuRequestDto = new MenuRequestDto("메뉴1", 1000, "메뉴설명1");

        Menu menu = menuRepository.save(new Menu(menuRequestDto, store));

        // 저장된 메뉴 조회
        Menu savedMenu = menuRepository.findById(menu.getId()).orElse(null);

        // 검증: 저장된 메뉴와 요청한 메뉴가 일치하는지 확인
        assertThat(savedMenu).isNotNull();
        assertThat(savedMenu.getName()).isEqualTo(menuRequestDto.getName());
        assertThat(savedMenu.getPrice()).isEqualTo(menuRequestDto.getPrice());
        assertThat(savedMenu.getDetails()).isEqualTo(menuRequestDto.getDetails());
    }

    @Test
    public void testUpdateMenu() {
        // 테스트용 Store 객체 생성 및 저장
        User user = createOwner1(); // User 객체를 적절히 생성해야 합니다.
        entityManager.persist(user);
        entityManager.flush();

        StoreRequestDto storeRequestDto = new StoreRequestDto("가게1", "설명1", "주소1");
        Store store = new Store(storeRequestDto, user);
        entityManager.persist(store);
        entityManager.flush();

        // 테스트용 MenuRequestDto 생성
        MenuRequestDto menuRequestDto = new MenuRequestDto("메뉴1", 1000, "메뉴설명1");

        Menu menu = menuRepository.save(new Menu(menuRequestDto, store));

        MenuRequestDto updateRequestDto = new MenuRequestDto("메뉴고침1", 9999, "메뉴설명고침1");
        menu.update(updateRequestDto);

        // 저장된 메뉴 조회
        Menu savedMenu = menuRepository.findById(menu.getId()).orElse(null);

        // 검증: 저장된 메뉴와 요청한 메뉴가 일치하는지 확인
        assertThat(savedMenu).isNotNull();
        assertThat(savedMenu.getName()).isEqualTo(updateRequestDto.getName());
        assertThat(savedMenu.getPrice()).isEqualTo(updateRequestDto.getPrice());
        assertThat(savedMenu.getDetails()).isEqualTo(updateRequestDto.getDetails());
    }

    @Test
    public void testDeleteMenu() {
        // 테스트용 Store 객체 생성 및 저장
        User user = createOwner1(); // User 객체를 적절히 생성해야 합니다.
        entityManager.persist(user);
        entityManager.flush();

        StoreRequestDto storeRequestDto = new StoreRequestDto("가게1", "설명1", "주소1");
        Store store = new Store(storeRequestDto, user);
        entityManager.persist(store);
        entityManager.flush();

        // 테스트용 MenuRequestDto 생성
        MenuRequestDto menuRequestDto = new MenuRequestDto("메뉴1", 1000, "메뉴설명1");

        Menu menu = menuRepository.save(new Menu(menuRequestDto, store));

        menuRepository.delete(menu);

        // 저장된 메뉴 조회
        Menu deletedMenu = menuRepository.findById(menu.getId()).orElse(null);

        // 검증: 저장된 메뉴와 요청한 메뉴가 일치하는지 확인
        assertThat(deletedMenu).isNull();
    }

    @Test
    public void testMenuDetails() {
        // 테스트용 Store 객체 생성 및 저장
        User user = createOwner1(); // User 객체를 적절히 생성해야 합니다.
        entityManager.persist(user);
        entityManager.flush();

        StoreRequestDto storeRequestDto = new StoreRequestDto("가게1", "설명1", "주소1");
        Store store = new Store(storeRequestDto, user);
        entityManager.persist(store);
        entityManager.flush();

        // 테스트용 MenuRequestDto 생성
        MenuRequestDto menuRequestDto = new MenuRequestDto("메뉴1", 1000, "메뉴설명1");

        Menu menu = menuRepository.save(new Menu(menuRequestDto, store));

        // 저장된 메뉴 조회
        Menu savedMenu = menuRepository.findById(menu.getId()).orElse(null);

        MenuResponseDto menuResponseDto = new MenuResponseDto(menu);

        assertThat(savedMenu.getName()).isEqualTo(menuResponseDto.getName());
        assertThat(savedMenu.getPrice()).isEqualTo(menuResponseDto.getPrice());
        assertThat(savedMenu.getDetails()).isEqualTo(menuResponseDto.getDetails());
    }

    private User createOwner1() {
        User owner = new User("testowner1", "testpassword",
                "test@example.com", UserRoleEnum.OWNER, 0L);
        return owner;
    }
}