package com.talentica.paymentservice.dto;

public class PaymentDtos {
    public record PurchaseRequest(String externalOrderId, String customerEmail, long amountCents, String currency, String description,
                                  String cardNumber, String cardExpiry, String cardCvv) {}
    public record PurchaseResponse(String orderId, String transactionId, String status) {}

    public record AuthorizeRequest(String externalOrderId, long amountCents, String currency,
                                   String cardNumber, String cardExpiry, String cardCvv) {}
    public record CaptureRequest(String orderId, long amountCents) {}
    public record VoidRequest(String orderId) {}
    public record RefundRequest(String orderId, long amountCents, String last4) {}
}

