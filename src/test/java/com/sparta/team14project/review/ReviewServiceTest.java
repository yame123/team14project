package com.sparta.team14project.review;

import com.sparta.team14project.order.dto.OrderRequestDto;
import com.sparta.team14project.order.entity.Cart;
import com.sparta.team14project.order.entity.Delivery;
import com.sparta.team14project.order.repository.OrderRepository;
import com.sparta.team14project.redis.CacheNames;
import com.sparta.team14project.review.dto.ReviewRequestDto;
import com.sparta.team14project.review.dto.ReviewResponseDto;
import com.sparta.team14project.review.entity.OrderReview;
import com.sparta.team14project.review.repository.OrderReviewRepository;
import com.sparta.team14project.store.dto.StoreRequestDto;
import com.sparta.team14project.store.entity.Store;
import com.sparta.team14project.store.repository.StoreRepository;
import com.sparta.team14project.user.entity.User;
import com.sparta.team14project.user.entity.UserRoleEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReviewServiceTest {

    @Mock
    private OrderReviewRepository reviewRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private StoreRepository storeRepository;

    @InjectMocks
    private ReviewService reviewService;

    private CacheManager cacheManager;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        // Set up cache manager with a mock cache
        cacheManager = new SimpleCacheManager();
        Cache cache = new ConcurrentMapCache(CacheNames.REVIEW_CACHE);
        ((SimpleCacheManager) cacheManager).setCaches(List.of(cache));
    }

    @Test
    @Transactional
    @CacheEvict(value = CacheNames.REVIEW_CACHE, allEntries = true)
    public void testCreateReview() {
        // Arrange
        Long orderId = 1L;
        ReviewRequestDto requestDto = createReviewRequest("Good!", 5, "Excellent");
        User user = createUser();
        // Create a cart for the user
        Cart cart = new Cart(user);
        Store store = createStore();
        Delivery delivery = new Delivery(createOrderRequest(), user, store);
        delivery.setId(orderId);
        delivery.setDelivered(true);
        delivery.setUser(user);
        store.setId(1L);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(delivery));
        when(storeRepository.findById(store.getId())).thenReturn(Optional.of(store));

        // Act
        ReviewResponseDto responseDto = reviewService.createReview(orderId, requestDto, user);

        // Assert (check if the responseDto has been created)
        System.out.println("requestDto.getTitle(): " + requestDto.getTitle() + " responseDto.getTitle(): " + responseDto.getTitle() );
        assertEquals(requestDto.getTitle(), responseDto.getTitle());
        // Add additional assertions based on the behavior of the createReview method
    }

    @Test
    @Transactional
    @CacheEvict(value = CacheNames.REVIEW_CACHE, allEntries = true)
    public void testUpdateReview() {
        // Arrange
        Long reviewId = 1L;
        ReviewRequestDto requestDto = createReviewRequest("Not bad!", 4, "Great!");
        User user = createUser();
        // Create a cart for the user
        Cart cart = new Cart(user);
        Store store = createStore();
        OrderReview review = createOrderReview();

        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));
        review.setDelivery(new Delivery(createOrderRequest(), user, store));
        review.getDelivery().setUser(user);

        // Act
        ReviewResponseDto responseDto = reviewService.updateReview(reviewId, requestDto, user);

        // Assert
        assertNotNull(responseDto);
        // Add additional assertions based on the behavior of the updateReview method
    }

    @Test
    @Cacheable(cacheNames = CacheNames.REVIEW_CACHE, key = "#storeId")
    public void testGetStoreReviews() {
        // Arrange
        Long storeId = 1L;
        List<OrderReview> reviewList = new ArrayList<>();
        Cart cart = new Cart(createUser());
        reviewList.add(createOrderReview());
        List<ReviewResponseDto> newReviewList = reviewToDto(reviewList);
        when(reviewRepository.findAllByStoreId(storeId)).thenReturn(reviewList);


        // Act
        Object result = reviewService.getStoreReviews(storeId);

        // Assert
        assertNotNull(result);
        assertTrue(result instanceof List);
        // Add additional assertions based on the behavior of the getStoreReviews method
    }

    private User createUser() {
        return new User("testuser", "testpassword",
                "test@example.com", UserRoleEnum.USER, 1000000L);
    }

    private OrderReview createOrderReview(){
        return new OrderReview(createReviewRequest("Mediocre!", 3, "Ok!"));
    }

    public List<ReviewResponseDto> reviewToDto(List<OrderReview> orderReviews){
        orderReviews.forEach(item -> item.setDelivery(new Delivery(createOrderRequest(), createUser(), createStore())));
        return orderReviews.stream().map(ReviewResponseDto::new).collect(Collectors.toList());
    }

    private Store createStore() {
        return new Store(createStoreRequest(), createUser());
    }

    private StoreRequestDto createStoreRequest(){
        // Mock the StoreRequestDto
        StoreRequestDto storeRequestDto = mock(StoreRequestDto.class);

        // Set up the behavior of the mock DTO
        when(storeRequestDto.getStoreName()).thenReturn("Mocked Store Name");
        when(storeRequestDto.getStoreAddress()).thenReturn("Mocked Store Address");
        when(storeRequestDto.getStoreDetails()).thenReturn("Mocked Store Details");
        return storeRequestDto;

    }

    private ReviewRequestDto createReviewRequest(String title, int star, String detail){
        // Mock the MenuRequestDto
        ReviewRequestDto requestDto = mock(ReviewRequestDto.class);

        // Set up the behavior of the mock DTO
        when(requestDto.getDetail()).thenReturn(detail);
        when(requestDto.getTitle()).thenReturn(title);
        when(requestDto.getStar()).thenReturn(star);
        return requestDto;
    }

    private OrderRequestDto createOrderRequest(){
        // Mock the MenuRequestDto
        OrderRequestDto requestDto = mock(OrderRequestDto.class);

        // Set up the behavior of the mock DTO
        when(requestDto.getAddress()).thenReturn("MyAddress");
        return requestDto;
    }
}

