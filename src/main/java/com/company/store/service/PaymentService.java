package com.company.store.service;

import com.company.store.model.Payment;
import com.company.store.model.PaymentType;

public interface PaymentService {

    Payment getPaymentByType(PaymentType paymentType);

    MethodPaymentService getPaymentService(PaymentType type);

}
