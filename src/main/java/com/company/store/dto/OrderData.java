package com.company.store.dto;

import com.company.store.model.PaymentType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderData {
    private PaymentType type;
    private List<OrderProductData> products;
}
