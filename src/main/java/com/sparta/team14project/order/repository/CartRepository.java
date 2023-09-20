package com.sparta.team14project.order.repository;

import com.sparta.team14project.order.entity.Cart;
import com.sparta.team14project.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository  extends JpaRepository<Cart,Long> {
    Cart findCartByUser(User user);
}
