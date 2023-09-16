package com.sparta.team14project.repository;

import com.sparta.team14project.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Delivery,Long> {
}
