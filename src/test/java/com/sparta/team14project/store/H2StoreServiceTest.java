package com.sparta.team14project.store;

import com.sparta.team14project.menu.dto.CookieMenuResponseDto;
import com.sparta.team14project.menu.dto.MenuRequestDto;
import com.sparta.team14project.menu.entity.Menu;
import com.sparta.team14project.menu.repository.MenuRepository;
import com.sparta.team14project.store.dto.StoreInfoResponseDto;
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
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class H2StoreServiceTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Test
    public void testCreateStore() {
        // 테스트용 Store 객체 생성 및 저장
        User user = createOwner1(); // User 객체를 적절히 생성해야 합니다.
        entityManager.persist(user);
        entityManager.flush();

        StoreRequestDto requestDto = new StoreRequestDto("가게1", "설명1", "주소1");
        Store store = new Store(requestDto, user);

        Store savedStore = storeRepository.save(store);

        // Repository에서 Store 객체를 조회
        Store foundStore = storeRepository.findStoreById(store.getId());

        // 검증
        assertThat(foundStore).isEqualTo(savedStore);
    }

    @Test
    public void testDeleteStore() {
        // 테스트용 Store 객체 생성 및 저장
        User user = createOwner1(); // User 객체를 적절히 생성해야 합니다.
        entityManager.persist(user);
        entityManager.flush();

        StoreRequestDto requestDto = new StoreRequestDto("가게1", "설명1", "주소1");
        Store store = new Store(requestDto, user);

        // storeRepository.save()를 사용하여 저장
        Store savedStore = storeRepository.save(store);

        // Repository에서 Store 객체를 조회
        Store foundStore = storeRepository.findStoreById(savedStore.getId());

        // 저장된 Store를 삭제
        storeRepository.delete(savedStore);

        // 삭제 후 조회
        Store deletedStore = storeRepository.findStoreById(savedStore.getId());

        // 검증: 삭제된 Store는 더 이상 존재하지 않아야 합니다.
        assertNull(deletedStore);
    }

    @Test
    public void testUpdateStore() {
        // 테스트용 User 객체 생성 및 저장
        User user = createOwner1(); // User 객체를 적절히 생성해야 합니다.
        entityManager.persist(user);
        entityManager.flush();

        // 테스트용 Store 객체 생성 및 저장
        StoreRequestDto requestDto = new StoreRequestDto("가게1", "설명1", "주소1");
        Store store = new Store(requestDto, user);
        entityManager.persist(store);
        entityManager.flush();

        // 수정할 내용을 담은 StoreRequestDto
        StoreRequestDto updatedDto = new StoreRequestDto("수정된 가게1", "수정된 설명1", "수정된 주소1");

        // Store 엔티티 업데이트
        store.update(updatedDto);

        // 업데이트된 Store 엔티티를 다시 조회
        Store foundStore = storeRepository.findStoreById(store.getId());

        // 검증: 업데이트된 내용이 올바르게 반영되었는지 확인
        assertThat(foundStore.getStoreName()).isEqualTo(updatedDto.getStoreName());
        assertThat(foundStore.getStoreDetails()).isEqualTo(updatedDto.getStoreDetails());
        assertThat(foundStore.getStoreAddress()).isEqualTo(updatedDto.getStoreAddress());
    }

    @Test
    @DirtiesContext
    public void testGetStores() {
        // 테스트용 Store 객체 생성 및 저장
        User user1 = createOwner1();
        entityManager.persist(user1);
        entityManager.flush();
        User user2 = createOwner2();
        entityManager.persist(user2);
        entityManager.flush();
        User user3 = createOwner3();
        entityManager.persist(user3);
        entityManager.flush();

        int size = storeRepository.findAll().size();

        // Store 엔티티 생성
        StoreRequestDto requestDto1 = createStoreRequestDto1();
        Store store1 = new Store(requestDto1, user1);
        storeRepository.save(store1);
        StoreRequestDto requestDto2 = createStoreRequestDto2();
        Store store2 = new Store(requestDto2, user2);
        storeRepository.save(store2);
        StoreRequestDto requestDto3 = createStoreRequestDto3();
        Store store3 = new Store(requestDto3, user3);
        storeRepository.save(store3);

        // findAll을 사용하여 모든 가게 검색
        List<Store> stores = storeRepository.findAll();

        // 검증: 저장된 가게의 수가 올바른지 확인
        assertThat(stores).hasSize(size + 3);

        // 가게 이름 비교
        assertThat(stores.get(size).getStoreName()).isEqualTo("가게1");
        assertThat(stores.get(size+1).getStoreName()).isEqualTo("가게2");
        assertThat(stores.get(size+2).getStoreName()).isEqualTo("가게3");
    }

    @Test
    @DirtiesContext
    public void testGetStoresRank() {
        // 테스트용 Store 객체 생성 및 저장
        User user1 = createOwner1();
        entityManager.persist(user1);
        entityManager.flush();
        User user2 = createOwner2();
        entityManager.persist(user2);
        entityManager.flush();
        User user3 = createOwner3();
        entityManager.persist(user3);
        entityManager.flush();

        List<Store> storesRank = storeRepository.findAllByOrderByStorePointDesc();
        int size = storesRank.size();
        int maxPoint = storesRank.get(0).getStorePoint();

        // Store 엔티티 생성
        StoreRequestDto requestDto1 = createStoreRequestDto1();
        Store store1 = new Store(requestDto1, user1);
        store1.setStorePoint(maxPoint + 2);
        storeRepository.save(store1);
        StoreRequestDto requestDto2 = createStoreRequestDto2();
        Store store2 = new Store(requestDto2, user2);
        store2.setStorePoint(maxPoint + 1);
        storeRepository.save(store2);
        StoreRequestDto requestDto3 = createStoreRequestDto3();
        Store store3 = new Store(requestDto3, user3);
        store3.setStorePoint(maxPoint + 3);
        storeRepository.save(store3);

        // findAll을 사용하여 모든 가게 검색
        List<Store> stores = storeRepository.findAllByOrderByStorePointDesc();

        // 검증: 저장된 가게의 수가 올바른지 확인
        assertThat(stores).hasSize(size + 3);

        // 가게 이름 비교
        assertThat(stores.get(0).getStoreName()).isEqualTo("가게3");
        assertThat(stores.get(1).getStoreName()).isEqualTo("가게1");
        assertThat(stores.get(2).getStoreName()).isEqualTo("가게2");
    }

    @Test
    @DirtiesContext
    public void testGetStoresByKeyword() {
        // 테스트용 Store 객체 생성 및 저장
        User user1 = createOwner1();
        entityManager.persist(user1);
        entityManager.flush();
        User user2 = createOwner2();
        entityManager.persist(user2);
        entityManager.flush();
        User user3 = createOwner3();
        entityManager.persist(user3);
        entityManager.flush();

        List<Store> storesRank = storeRepository.findAllByStoreNameContaining("가게1");
        int size = storesRank.size();

        // Store 엔티티 생성
        StoreRequestDto requestDto1 = createStoreRequestDto1();
        Store store1 = new Store(requestDto1, user1);
        storeRepository.save(store1);
        StoreRequestDto requestDto2 = createStoreRequestDto2();
        Store store2 = new Store(requestDto2, user2);
        storeRepository.save(store2);
        StoreRequestDto requestDto3 = createStoreRequestDto3();
        Store store3 = new Store(requestDto3, user3);
        storeRepository.save(store3);

        // findAll을 사용하여 모든 가게 검색
        List<Store> stores = storeRepository.findAllByStoreNameContaining("가게1");

        // 검증: 저장된 가게의 수가 올바른지 확인
        assertThat(stores).hasSize(size + 1);

        // 가게 이름 비교
        assertThat(stores.get(size).getStoreName()).isEqualTo("가게1");
    }

    @Test
    @DirtiesContext
    public void testGetStoreInfo() {
        // 테스트용 Store 객체 생성 및 저장
        User user1 = createOwner1();
        entityManager.persist(user1);
        entityManager.flush();

        // Store 엔티티 생성
        StoreRequestDto requestDto1 = createStoreRequestDto1();
        Store store = new Store(requestDto1, user1);
        storeRepository.save(store);

        MenuRequestDto menuRequestDto = new MenuRequestDto("메뉴1",1000,"메뉴설명1");

        Menu menu = menuRepository.save(new Menu(menuRequestDto, store));

        store.getMenuList().add(menu);

        List<CookieMenuResponseDto> menuResponseDtoList = store.getMenuList().stream().map(CookieMenuResponseDto::new).toList();

        StoreInfoResponseDto storeInfoResponseDto = new StoreInfoResponseDto(store, menuResponseDtoList);

        assertThat(storeInfoResponseDto.getStoreName()).isEqualTo("가게1");
        assertThat(storeInfoResponseDto.getMenuList().get(0).getName()).isEqualTo("메뉴1");
    }



    private User createOwner1() {
        User owner = new User("testowner1", "testpassword",
                "test@example.com", UserRoleEnum.OWNER, 0L);
        return owner;
    }
    private User createOwner2() {
        User owner = new User("testowner2", "testpassword",
                "test@example.com", UserRoleEnum.OWNER, 0L);
        return owner;
    }
    private User createOwner3() {
        User owner = new User("testowner3", "testpassword",
                "test@example.com", UserRoleEnum.OWNER, 0L);
        return owner;
    }
    private StoreRequestDto createStoreRequestDto1() {
        return new StoreRequestDto("가게1", "설명1", "주소1");
    }
    private StoreRequestDto createStoreRequestDto2() {
        return new StoreRequestDto("가게2", "설명2", "주소2");
    }
    private StoreRequestDto createStoreRequestDto3() {
        return new StoreRequestDto("가게3", "설명3", "주소3");
    }
}
