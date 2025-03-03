package com.company.store.dto;

import com.company.store.model.OrderStatus;
import com.company.store.model.PaymentType;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
public class OrderDto {
    private Long orderId;
    private Long customerId;
    private PaymentType paymentType;
    private List<OrderProductInfoDto> products;
    private OrderStatus status;
    private BigDecimal totalPrice;
    private String errorMessage;
}
