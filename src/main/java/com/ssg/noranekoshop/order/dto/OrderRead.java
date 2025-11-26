package com.ssg.noranekoshop.order.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ssg.noranekoshop.item.dto.ItemRead;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class OrderRead {
    private Integer id;
    private String name;
    private String address;
    private String payment;
    private Long amount;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime created;
    private List<ItemRead> items;
}
