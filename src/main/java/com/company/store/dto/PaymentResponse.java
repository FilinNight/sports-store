package com.company.store.dto;

import com.company.store.model.PaymentStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaymentResponse {
    private Long orderId;
    private PaymentStatus status;
    private String errorMessage;

    @Override
    public String toString() {
        return "PaymentResponse{" +
                "orderId=" + orderId +
                ", status=" + status +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
