package com.ssg.noranekoshop.order.service;

import com.ssg.noranekoshop.cart.service.CartService;
import com.ssg.noranekoshop.item.service.ItemService;
import com.ssg.noranekoshop.order.entity.OrderItem;
import com.ssg.noranekoshop.order.repository.OrderItemRepository;
import com.ssg.noranekoshop.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BaseOrderItemService implements OrderItemService {

    private final OrderItemRepository orderItemRepository;

    // 주문 목록 조회
    @Override
    public List<OrderItem> findAll(Integer orderId) {
        return orderItemRepository.findAllByOrderId(orderId);
    }

    // 주문 상품 데이터 저장
    @Override
    public void saveAll(List<OrderItem> orderItems) {
        orderItemRepository.saveAll(orderItems);
    }
}
