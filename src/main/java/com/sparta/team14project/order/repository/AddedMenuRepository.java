package com.sparta.team14project.order.repository;

import com.sparta.team14project.order.entity.AddedMenu;
import com.sparta.team14project.order.entity.Cart;
import com.sparta.team14project.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddedMenuRepository extends JpaRepository<AddedMenu,Long> {



    Optional<AddedMenu> findAddedMenuByCartAndMenu(Cart cart, Menu menu);
}
