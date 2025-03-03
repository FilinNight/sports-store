package com.company.store.service;


import com.company.store.dto.PaymentRequest;
import com.company.store.dto.PaymentResponse;
import com.company.store.model.PaymentType;

public interface MethodPaymentService {

    PaymentResponse pay(PaymentRequest request);

    Boolean hasType(PaymentType type);

}
