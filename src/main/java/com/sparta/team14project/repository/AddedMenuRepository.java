package com.sparta.team14project.repository;

import com.sparta.team14project.entity.AddedMenu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddedMenuRepository extends JpaRepository<AddedMenu,Long> {
    AddedMenu findByCartIdAndMenuId(Long cartId, Long menuId);
}
