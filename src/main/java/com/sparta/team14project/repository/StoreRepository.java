package com.sparta.team14project.repository;

import com.sparta.team14project.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long>{
    List<Store> findAllByStoreNameContains(String Keyword);
}
