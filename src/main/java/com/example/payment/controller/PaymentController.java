package com.talentica.paymentservice.controller;

import com.talentica.paymentservice.dto.PaymentDtos.*;
import com.talentica.paymentservice.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/purchase")
    public ResponseEntity<PurchaseResponse> purchase(@RequestBody @Validated PurchaseRequest request) {
        return ResponseEntity.ok(paymentService.purchase(request));
    }

    @PostMapping("/authorize")
    public ResponseEntity<PurchaseResponse> authorize(@RequestBody @Validated AuthorizeRequest request) {
        return ResponseEntity.ok(paymentService.authorize(request));
    }

    @PostMapping("/capture")
    public ResponseEntity<PurchaseResponse> capture(@RequestBody @Validated CaptureRequest request) {
        return ResponseEntity.ok(paymentService.capture(request));
    }

    @PostMapping("/void")
    public ResponseEntity<PurchaseResponse> voidOrder(@RequestBody @Validated VoidRequest request) {
        return ResponseEntity.ok(paymentService.voidOrder(request));
    }

    @PostMapping("/refund")
    public ResponseEntity<PurchaseResponse> refund(@RequestBody @Validated RefundRequest request) {
        return ResponseEntity.ok(paymentService.refund(request));
    }
}

