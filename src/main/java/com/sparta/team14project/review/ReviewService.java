package com.sparta.team14project.review;

import com.sparta.team14project.order.entity.Delivery;
import com.sparta.team14project.order.repository.OrderRepository;
import com.sparta.team14project.review.repository.OrderReviewRepository;
import com.sparta.team14project.redis.CacheNames;
import com.sparta.team14project.review.dto.ReviewRequestDto;
import com.sparta.team14project.review.dto.ReviewResponseDto;
import com.sparta.team14project.review.entity.OrderReview;
import com.sparta.team14project.store.entity.Store;
import com.sparta.team14project.store.repository.StoreRepository;
import com.sparta.team14project.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final OrderReviewRepository reviewRepository;
    private final OrderRepository orderRepository;
    private final StoreRepository storeRepository;

    @Transactional
    @CacheEvict(value = CacheNames.REVIEW_CACHE, allEntries = true)
    public ReviewResponseDto createReview(Long orderId, ReviewRequestDto requestDto, User user) {
        Delivery delivery = findById(orderId); // 배달 찿기
        if(!delivery.isDelivered()){ // 리뷰 유효성 검사
            throw new IllegalArgumentException("완료된 배달에 대해서만 리뷰를 작성하실 수 있습니다.");
        } else if(delivery.getUser().getId() != user.getId()){
            throw new IllegalArgumentException("주문한 사람만 리뷰를 작성할 수 있습니다.");
        }
        Store store = storeRepository.findById(delivery.getStore().getId()).orElseThrow(()->new NullPointerException("가게 정보를 찾을 수 없습니다."));
        store.addStar(requestDto.getStar());
        OrderReview review = new OrderReview(requestDto);
        review.setDelivery(delivery);
        review.setStoreId(store.getId());
        reviewRepository.save(review);

        return new ReviewResponseDto(review);
    }

    @Transactional
    @CacheEvict(value = CacheNames.REVIEW_CACHE, allEntries = true)
    public ReviewResponseDto updateReview(Long reviewId, ReviewRequestDto requestDto, User user) {
        OrderReview review = findReviewById(reviewId); // 리뷰 찾기
        if(review.getDelivery().getUser().getId() != user.getId()) // 리뷰 수정 유효성 검사
            throw new IllegalArgumentException("수정 권한이 없는 사용자입니다.");
        review.update(requestDto);
        return new ReviewResponseDto(review);
    }

    private Delivery findById(Long id){
        return orderRepository.findById(id).orElseThrow(()->new NullPointerException("주문 정보를 찾을 수 없습니다."));
    }

    private OrderReview findReviewById(Long id){
        return reviewRepository.findById(id).orElseThrow(()->new NullPointerException("리뷰 정보를 찾을 수 없습니다."));
    }

//    public List<ReviewResponseDto> getStoreReviews(Long storeId) {
//        List<OrderReview> reviewList = reviewRepository.findAllByStoreId(storeId);
//        // OrderReview를 ReviewResponseDto로 변환하여 리스트로 반환
//        return reviewList.stream().map(ReviewResponseDto::new).collect(Collectors.toList());
//    }


    @Cacheable(cacheNames = CacheNames.REVIEW_CACHE, key = "#storeId")
    public Object getStoreReviews(Long storeId) {
//        List<OrderReview> reviewList = reviewRepository.findAllByStoreId(storeId);
        // OrderReview를 ReviewResponseDto로 변환하여 리스트로 반환
        return reviewRepository.findAllByStoreId(storeId).stream().map(ReviewResponseDto::new).collect(Collectors.toList());
    }
}
