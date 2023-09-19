package com.sparta.team14project.menu.repository;

import com.sparta.team14project.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    Optional<Menu> findMenuById(Long id);
}
