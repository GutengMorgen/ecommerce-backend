package com.alumnione.ecommerce.controller;

import com.alumnione.ecommerce.dto.CartReturnDto;
import com.alumnione.ecommerce.service.CartServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "cart")
@RequiredArgsConstructor
public class CartController {
    private final CartServiceImp service;

    @PostMapping
    public ResponseEntity<String> createCart() {
        //TODO: a cart will be create automatically when a user is created
        //TODO: handle the errors
        return null;
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<CartReturnDto> get(@PathVariable Long id) {
        //TODO: create a custom query to get items
        return service.get(id);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<CartReturnDto> deleteAll(@PathVariable Long id) {
        return service.deleteAll(id);
    }

    @PostMapping(path = "{id}/item/{idItem}")
    public ResponseEntity<CartReturnDto> addItem(@PathVariable Long id, @PathVariable Long idItem) {
        return service.addItem(id, idItem);
    }

    @DeleteMapping(path = "{id}/item/{cellId}")
    public ResponseEntity<CartReturnDto> deleteItem(@PathVariable Long id, @PathVariable long cellId) {
        return service.deleteItem(id, cellId);
    }
}