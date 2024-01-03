package com.alumnione.ecommerce.dto;

import com.alumnione.ecommerce.entity.Cellphone;

public record CellphoneBasicReturnDto(Long id, String brand, String model, String image, int stock,
                                      short quantity) {
    public CellphoneBasicReturnDto(Cellphone c, short quantity) {
        this(c.getId(), c.getBrand(), c.getModel(), c.getImage(), (Integer) c.getStock(), quantity);
    }
}
