package com.ssg.noranekoshop.item.controller;

import com.ssg.noranekoshop.item.dto.ItemRead;
import com.ssg.noranekoshop.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

//    @GetMapping("/api/items")
//    public ResponseEntity<?> readAll(){
//        List<ItemRead> items = itemService.findAll();
//        return new ResponseEntity<>(items, HttpStatus.OK);
//    }

    @GetMapping("/api/items")
    public ResponseEntity<?> readAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size
    ){
        Page<ItemRead> items = itemService.getItems(page, size);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }
}
