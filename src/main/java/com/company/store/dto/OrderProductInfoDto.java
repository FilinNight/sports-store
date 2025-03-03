package com.company.store.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class OrderProductInfoDto {
    private Long orderProductInfoId;
    private Long productId;
    private String name;
    private String article;
    private String description;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal totalPrice;
}
