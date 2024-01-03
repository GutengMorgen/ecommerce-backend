package com.alumnione.ecommerce.dto;

import com.alumnione.ecommerce.entity.Cellphone;

public record CellphoneBasicReturnDto(Long id, String brand, String model, String image, Integer stock,
                                      Integer quantity) {
    public CellphoneBasicReturnDto(Cellphone c) {
        this(c.getId(), c.getBrand(), c.getModel(), c.getImage(), c.getStock(), c.getQuantity());
    }
}
