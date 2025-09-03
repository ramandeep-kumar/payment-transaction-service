package com.talentica.paymentservice.controller;

import com.talentica.paymentservice.model.PaymentRequest;
import com.talentica.paymentservice.model.PaymentResponse;
import com.talentica.paymentservice.service.PaymentService;
import com.talentica.paymentservice.entity.Transaction;
import com.talentica.paymentservice.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private TransactionRepository transactionRepository;

    @PostMapping("/purchase")
    public PaymentResponse purchase(@RequestBody PaymentRequest request) {
        return paymentService.purchase(request);
    }

    @PostMapping("/authorize")
    public PaymentResponse authorize(@RequestBody PaymentRequest request) {
        return paymentService.authorize(request);
    }

    @PostMapping("/capture/{transactionId}")
    public PaymentResponse capture(@PathVariable String transactionId) {
        return paymentService.capture(transactionId);
    }

    @PostMapping("/cancel/{transactionId}")
    public PaymentResponse cancel(@PathVariable String transactionId) {
        return paymentService.cancel(transactionId);
    }

    @PostMapping("/refund/{transactionId}")
    public PaymentResponse refund(@PathVariable String transactionId, @RequestParam Double amount) {
        return paymentService.refund(transactionId, amount);
    }

    @GetMapping("/history/{orderId}")
    public List<Transaction> getTransactionHistory(@PathVariable Long orderId) {
        return transactionRepository.findByOrderId(orderId);
    }
}
