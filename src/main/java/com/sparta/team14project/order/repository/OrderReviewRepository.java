package com.sparta.team14project.order.repository;

import com.sparta.team14project.review.entity.OrderReview;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderReviewRepository extends JpaRepository<OrderReview,Long> {
}
