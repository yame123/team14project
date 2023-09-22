package com.sparta.team14project.order.repository;

import com.sparta.team14project.order.dto.OrderResponseDto;
import com.sparta.team14project.order.entity.Delivery;
import com.sparta.team14project.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Delivery,Long> {
    List<Delivery> findAllByUser(User user);
}
