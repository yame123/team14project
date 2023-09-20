package com.sparta.team14project.store;

import com.sparta.team14project.order.repository.DeliveryRepository;
import com.sparta.team14project.store.dto.StoreRequestDto;
import com.sparta.team14project.store.dto.StoreResponseDto;
import com.sparta.team14project.store.entity.Store;
import com.sparta.team14project.store.repository.StoreRepository;
import com.sparta.team14project.user.entity.User;
import com.sparta.team14project.user.entity.UserRoleEnum;
import com.sparta.team14project.user.login.security.UserDetailsImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StoreServiceTest {
    @Mock
    StoreRepository storeRepository;

    @Mock
    DeliveryRepository deliveryRepository;

    private StoreService storeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Mock 객체 초기화
        storeService = new StoreService(storeRepository, null); // DeliveryRepository는 Mock으로 대체
    }

    @Test
    @DisplayName("신규 가게 생성")
    void testCreateStore() {
        String storeName = "테스트 가게";
        String storeDetails = "테스트를 파는 가게";
        String storeAddress = "00시 00동 00로 00-00";
        // Given
        StoreRequestDto requestDto = new StoreRequestDto(storeName, storeDetails, storeAddress);

        User owner = createOwner();

        UserDetailsImpl userDetails = new UserDetailsImpl(owner);

        // When
        // Mockito를 사용하여 storeRepository.save 메서드의 동작을 설정,
        // 없을경우 Mock 객체가 설정 되지 않아서 storeRepository.save 메서드가 호출되어도 기본 동작인 null을 반환
        when(storeRepository.save(any(Store.class))).thenAnswer(invocation -> {
            Store savedStore = invocation.getArgument(0);
            savedStore.setId(1L); // 저장 후 ID 설정
            return savedStore;
        });

        StoreResponseDto responseDto = storeService.createStore(requestDto, userDetails);

        // Then
        verify(storeRepository, times(1)).save(any(Store.class));
        assertEquals("테스트 가게", responseDto.getStoreName());
        System.out.println(responseDto.getStoreName());
        assertEquals("테스트를 파는 가게", responseDto.getStoreDetails());
        System.out.println(responseDto.getStoreDetails());
        assertEquals("00시 00동 00로 00-00", responseDto.getStoreAddress());
        System.out.println(responseDto.getStoreAddress());
        // 원하는 추가 검증을 수행할 수 있습니다.
    }



    private User createOwner() {
        User owner = new User("testowner", "testpassword",
                "test@example.com", UserRoleEnum.OWNER, 0L);
        return owner;
    }
    private User createUser() {
        return new User("testuser", "testpassword",
                "test@example.com", UserRoleEnum.USER, 1000000L);
    }
}