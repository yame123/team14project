package com.sparta.team14project.review;

import com.sparta.team14project.dto.ReviewRequestDto;
import com.sparta.team14project.dto.ReviewResponseDto;
import com.sparta.team14project.entity.Order;
import com.sparta.team14project.entity.OrderReview;
import com.sparta.team14project.entity.User;
import com.sparta.team14project.repository.OrderRepository;
import com.sparta.team14project.repository.OrderReviewRepository;
import jakarta.validation.constraints.Null;
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
        Order order = findById(orderId);
        if(!order.isDelivered()){
            throw new IllegalArgumentException("완료된 배달에 대해서만 리뷰를 작성하실 수 있습니다.");
        }
        review.setOrder(order);
        reviewRepository.save(review);

        return new ReviewResponseDto(review);
    }

    @Transactional
    public String updateReview(Long reviewId, ReviewRequestDto requestDto, User user) {
        OrderReview review = findReviewById(reviewId);
        if(review.getOrder().getUser().getId() != user.getId())
            throw new IllegalArgumentException("수정 권한이 없는 사용자입니다.");
        review.update(requestDto);
        return "{statusCode: 200, msg: “리뷰가 수정 되었습니다.”}";
    }

    private Order findById(Long id){
        return orderRepository.findById(id).orElseThrow(()->new NullPointerException("주문 정보를 찾을 수 없습니다."));
    }

    private OrderReview findReviewById(Long id){
        return reviewRepository.findById(id).orElseThrow(()->new NullPointerException("리뷰 정보를 찾을 수 없습니다."));
    }
}
