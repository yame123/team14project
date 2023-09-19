package com.sparta.team14project.order.repository;

import com.sparta.team14project.order.entity.Delivery;
import com.sparta.team14project.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    List<Delivery> findAllByStore(Store store);
}
