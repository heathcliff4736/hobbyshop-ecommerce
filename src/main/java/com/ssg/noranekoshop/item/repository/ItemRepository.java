package com.ssg.noranekoshop.item.repository;

import com.ssg.noranekoshop.item.dto.ItemRead;
import com.ssg.noranekoshop.item.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer> {

    // 여러 상품 아이디로 상품 데이터를 조회하는 메서드
    List<Item> findAllByIdIn(List<Integer> ids);
    
    // 페이징 적용
    Page<Item> findAll(Pageable pageable);

    Page<Item> findByIdIn(List<Integer> ids, Pageable pageable);
}
