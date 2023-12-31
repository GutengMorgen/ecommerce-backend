package com.alumnione.ecommerce.service;

import com.alumnione.ecommerce.dto.CartReturnDto;
import org.springframework.http.ResponseEntity;

public interface CartService {
    ResponseEntity<CartReturnDto> get(long id);
    ResponseEntity<CartReturnDto> deleteAll(long id);
    ResponseEntity<CartReturnDto> addItem(long cartId, long cellId);
    ResponseEntity<CartReturnDto> deleteItem(long id, short index);
}
