package com.talentica.paymentservice.service;

import com.talentica.paymentservice.model.PaymentRequest;
import com.talentica.paymentservice.model.PaymentResponse;

public interface PaymentService {
    PaymentResponse purchase(PaymentRequest request);
    PaymentResponse authorize(PaymentRequest request);
    PaymentResponse capture(String transactionId);
    PaymentResponse cancel(String transactionId);
    PaymentResponse refund(String transactionId, Double amount);
}
