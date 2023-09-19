package com.sparta.team14project.order.repository;

import com.sparta.team14project.order.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Delivery,Long> {
}
