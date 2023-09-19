package com.sparta.team14project.review;

import com.sparta.team14project.order.entity.Delivery;
import com.sparta.team14project.review.entity.OrderReview;
import com.sparta.team14project.review.dto.ReviewRequestDto;
import com.sparta.team14project.review.dto.ReviewResponseDto;
import com.sparta.team14project.user.entity.User;
import com.sparta.team14project.order.repository.OrderRepository;
import com.sparta.team14project.order.repository.OrderReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final OrderReviewRepository reviewRepository;
    private final OrderRepository orderRepository;
    public ReviewResponseDto createReview(Long orderId, ReviewRequestDto requestDto, User user) {
        OrderReview review = new OrderReview(requestDto);
        Delivery delivery = findById(orderId);
//        if(!delivery.isDelivered()){
//            throw new IllegalArgumentException("완료된 배달에 대해서만 리뷰를 작성하실 수 있습니다.");
//        }
        review.setDelivery(delivery);
        reviewRepository.save(review);

        return new ReviewResponseDto(review);
    }

    @Transactional
    public String updateReview(Long reviewId, ReviewRequestDto requestDto, User user) {
        OrderReview review = findReviewById(reviewId);
        if(review.getDelivery().getUser().getId() != user.getId())
            throw new IllegalArgumentException("수정 권한이 없는 사용자입니다.");
        review.update(requestDto);
        return "{statusCode: 200, msg: “리뷰가 수정 되었습니다.”}";
    }

    private Delivery findById(Long id){
        return orderRepository.findById(id).orElseThrow(()->new NullPointerException("주문 정보를 찾을 수 없습니다."));
    }

    private OrderReview findReviewById(Long id){
        return reviewRepository.findById(id).orElseThrow(()->new NullPointerException("리뷰 정보를 찾을 수 없습니다."));
    }
}
