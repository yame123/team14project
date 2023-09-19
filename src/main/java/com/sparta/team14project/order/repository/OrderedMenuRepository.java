package com.sparta.team14project.order.repository;

import com.sparta.team14project.order.entity.OrderedMenu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderedMenuRepository extends JpaRepository<OrderedMenu,Long> {

}
