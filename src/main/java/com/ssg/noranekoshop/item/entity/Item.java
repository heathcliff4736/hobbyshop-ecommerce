package com.ssg.noranekoshop.item.entity;

import com.ssg.noranekoshop.item.dto.ItemRead;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name="items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200, nullable = false)
    private String name;

    @Column(length = 100, nullable = false)
    private String imgPath;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int discountPer;

    @Column(updatable = false, nullable = false)
    @CreationTimestamp
    private LocalDateTime created;

    // 상품 조회 DTO 변환
    // 엔티티 객체를 상팜 조회 DTO로 변환하는 메서드이다.
    // 빌더를 활용하여 필드의 값을 간편하게 초기화하고 객체를 생성한다.
    // 상품 서비스에서 데이터를 조회할 때 사용
    public ItemRead toRead() {
        return ItemRead.builder()
                .id(id)
                .name(name)
                .imgPath(imgPath)
                .price(price)
                .discountPer(discountPer)
                .build();
    }
}
