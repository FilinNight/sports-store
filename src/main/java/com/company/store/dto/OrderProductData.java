package com.company.store.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderProductData {
    private Long productId;
    private Integer quantity;
    private BigDecimal price;
}
