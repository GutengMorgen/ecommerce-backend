package com.alumnione.ecommerce.dto;

import com.alumnione.ecommerce.entity.Cart;
import java.time.LocalDateTime;
import java.util.List;

public record CartReturnDto(LocalDateTime lastUpdated, List<CellphoneBasicReturnDto> cellphones) {
    public CartReturnDto(Cart cart) {
        this(cart.getLastUpdated(), cart.getCellphones().stream().map(CellphoneBasicReturnDto::new).toList());
    }
}
