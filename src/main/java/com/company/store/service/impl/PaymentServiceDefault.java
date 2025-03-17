package com.company.store.service.impl;

import com.company.store.model.Payment;
import com.company.store.model.PaymentType;
import com.company.store.repository.PaymentRepository;
import com.company.store.service.MethodPaymentService;
import com.company.store.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PaymentServiceDefault implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final List<MethodPaymentService> methodPaymentServices;

    @Override
    public Payment getPaymentByType(PaymentType type) {
        return paymentRepository.findByType(type)
                .orElseThrow(() -> new IllegalArgumentException("Payment with type " + type + " not found"));
    }

    @Override
    public MethodPaymentService getPaymentService(PaymentType type) {
        return methodPaymentServices.stream()
                .filter(ps -> ps.hasType(type))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No payment service found for type " + type));
    }
}
