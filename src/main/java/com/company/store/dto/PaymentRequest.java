package com.company.store.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequest {
    private OrderDto order;
    private String additionalInfo;
}
