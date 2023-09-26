package com.sparta.team14project.store.repository;

import com.sparta.team14project.store.entity.Store;
import com.sparta.team14project.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long>{
    List<Store> findAllByStoreNameContaining(String Keyword);
    Store findStoreByUser(User user);
    Store findStoreById(Long id);

    List<Store> findAllByOrderByStorePointDesc();
}
