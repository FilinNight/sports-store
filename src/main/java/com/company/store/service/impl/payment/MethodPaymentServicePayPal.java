package com.company.store.service.impl.payment;

import com.company.store.dto.PaymentRequest;
import com.company.store.dto.PaymentResponse;
import com.company.store.model.PaymentStatus;
import com.company.store.model.PaymentType;
import com.company.store.service.MethodPaymentService;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class MethodPaymentServicePayPal implements MethodPaymentService {

    private final PaymentType paymentType = PaymentType.PAY_PAL;

    @Override
    public PaymentResponse pay(PaymentRequest request) {
        Random random = new Random();
        int randomNumber = random.nextInt(1000);
        try {
            Thread.sleep(randomNumber);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return PaymentResponse.builder()
                .status(PaymentStatus.ERROR)
                .build();
    }

    @Override
    public Boolean hasType(PaymentType type) {
        return type.equals(paymentType);
    }
}
