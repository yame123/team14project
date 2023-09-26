package com.sparta.team14project.review.repository;

import com.sparta.team14project.review.entity.OrderReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface OrderReviewRepository extends JpaRepository<OrderReview,Long> {
    List<OrderReview> findAllByStoreId(Long storeId);
}
