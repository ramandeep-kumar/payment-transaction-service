package com.example.payment.service;

import com.example.payment.dto.PaymentDtos.*;
import com.example.payment.entity.Order;
import com.example.payment.entity.Transaction;
import com.example.payment.gateway.AuthorizeNetClient;
import com.example.payment.repository.OrderRepository;
import com.example.payment.repository.TransactionRepository;
import net.authorize.api.contract.v1.CreateTransactionResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class PaymentService {
    private final OrderRepository orderRepository;
    private final TransactionRepository transactionRepository;
    private final AuthorizeNetClient authorizeNetClient;

    public PaymentService(OrderRepository orderRepository, TransactionRepository transactionRepository, AuthorizeNetClient authorizeNetClient) {
        this.orderRepository = orderRepository;
        this.transactionRepository = transactionRepository;
        this.authorizeNetClient = authorizeNetClient;
    }

    @Transactional
    public PurchaseResponse purchase(PurchaseRequest req) {
        Order order = new Order();
        order.setExternalOrderId(req.externalOrderId());
        order.setCustomerEmail(req.customerEmail());
        order.setAmountCents(req.amountCents());
        order.setCurrency(req.currency());
        order.setStatus("PROCESSING");
        order.setDescription(req.description());
        order = orderRepository.save(order);

        CreateTransactionResponse resp = authorizeNetClient.purchase(req.cardNumber(), req.cardExpiry(), req.cardCvv(), req.amountCents());
        String status = resp != null && resp.getMessages() != null && resp.getMessages().getResultCode() == net.authorize.api.contract.v1.MessageTypeEnum.OK ? "SUCCEEDED" : "FAILED";
        String txnId = resp != null && resp.getTransactionResponse() != null ? resp.getTransactionResponse().getTransId() : null;

        Transaction txn = new Transaction();
        txn.setOrder(order);
        txn.setType("PURCHASE");
        txn.setAmountCents(req.amountCents());
        txn.setStatus(status);
        txn.setGatewayTransactionId(txnId);
        transactionRepository.save(txn);

        order.setStatus(status.equals("SUCCEEDED") ? "CAPTURED" : "FAILED");
        orderRepository.save(order);
        return new PurchaseResponse(order.getId().toString(), txnId, order.getStatus());
    }

    @Transactional
    public PurchaseResponse authorize(AuthorizeRequest req) {
        Order order = orderRepository.findByExternalOrderId(req.externalOrderId()).orElseGet(() -> {
            Order o = new Order();
            o.setExternalOrderId(req.externalOrderId());
            o.setAmountCents(req.amountCents());
            o.setCurrency(req.currency());
            o.setStatus("PROCESSING");
            return orderRepository.save(o);
        });

        CreateTransactionResponse resp = authorizeNetClient.authorize(req.cardNumber(), req.cardExpiry(), req.cardCvv(), req.amountCents());
        String status = resp != null && resp.getMessages() != null && resp.getMessages().getResultCode() == net.authorize.api.contract.v1.MessageTypeEnum.OK ? "SUCCEEDED" : "FAILED";
        String txnId = resp != null && resp.getTransactionResponse() != null ? resp.getTransactionResponse().getTransId() : null;

        Transaction txn = new Transaction();
        txn.setOrder(order);
        txn.setType("AUTHORIZE");
        txn.setAmountCents(req.amountCents());
        txn.setStatus(status);
        txn.setGatewayTransactionId(txnId);
        transactionRepository.save(txn);

        order.setStatus(status.equals("SUCCEEDED") ? "AUTHORIZED" : "FAILED");
        orderRepository.save(order);
        return new PurchaseResponse(order.getId().toString(), txnId, order.getStatus());
    }

    @Transactional
    public PurchaseResponse capture(CaptureRequest req) {
        Order order = orderRepository.findById(UUID.fromString(req.orderId())).orElseThrow();
        Transaction authTxn = transactionRepository.findAll().stream()
                .filter(t -> t.getOrder().getId().equals(order.getId()) && "AUTHORIZE".equals(t.getType()))
                .reduce((first, second) -> second).orElseThrow();

        CreateTransactionResponse resp = authorizeNetClient.capture(authTxn.getGatewayTransactionId(), req.amountCents());
        String status = resp != null && resp.getMessages() != null && resp.getMessages().getResultCode() == net.authorize.api.contract.v1.MessageTypeEnum.OK ? "SUCCEEDED" : "FAILED";
        String txnId = resp != null && resp.getTransactionResponse() != null ? resp.getTransactionResponse().getTransId() : null;

        Transaction txn = new Transaction();
        txn.setOrder(order);
        txn.setType("CAPTURE");
        txn.setAmountCents(req.amountCents());
        txn.setStatus(status);
        txn.setGatewayTransactionId(txnId);
        transactionRepository.save(txn);

        order.setStatus(status.equals("SUCCEEDED") ? "CAPTURED" : "FAILED");
        orderRepository.save(order);
        return new PurchaseResponse(order.getId().toString(), txnId, order.getStatus());
    }

    @Transactional
    public PurchaseResponse voidOrder(VoidRequest req) {
        Order order = orderRepository.findById(UUID.fromString(req.orderId())).orElseThrow();
        Transaction lastTxn = transactionRepository.findAll().stream()
                .filter(t -> t.getOrder().getId().equals(order.getId()))
                .reduce((first, second) -> second).orElseThrow();

        CreateTransactionResponse resp = authorizeNetClient.voidTransaction(lastTxn.getGatewayTransactionId());
        String status = resp != null && resp.getMessages() != null && resp.getMessages().getResultCode() == net.authorize.api.contract.v1.MessageTypeEnum.OK ? "SUCCEEDED" : "FAILED";
        String txnId = resp != null && resp.getTransactionResponse() != null ? resp.getTransactionResponse().getTransId() : null;

        Transaction txn = new Transaction();
        txn.setOrder(order);
        txn.setType("VOID");
        txn.setAmountCents(lastTxn.getAmountCents());
        txn.setStatus(status);
        txn.setGatewayTransactionId(txnId);
        transactionRepository.save(txn);

        order.setStatus(status.equals("SUCCEEDED") ? "VOIDED" : "FAILED");
        orderRepository.save(order);
        return new PurchaseResponse(order.getId().toString(), txnId, order.getStatus());
    }

    @Transactional
    public PurchaseResponse refund(RefundRequest req) {
        Order order = orderRepository.findById(UUID.fromString(req.orderId())).orElseThrow();
        Transaction lastSettled = transactionRepository.findAll().stream()
                .filter(t -> t.getOrder().getId().equals(order.getId()) && ("CAPTURE".equals(t.getType()) || "PURCHASE".equals(t.getType())))
                .reduce((first, second) -> second).orElseThrow();

        CreateTransactionResponse resp = authorizeNetClient.refund(lastSettled.getGatewayTransactionId(), req.last4(), req.amountCents());
        String status = resp != null && resp.getMessages() != null && resp.getMessages().getResultCode() == net.authorize.api.contract.v1.MessageTypeEnum.OK ? "SUCCEEDED" : "FAILED";
        String txnId = resp != null && resp.getTransactionResponse() != null ? resp.getTransactionResponse().getTransId() : null;

        Transaction txn = new Transaction();
        txn.setOrder(order);
        txn.setType("REFUND");
        txn.setAmountCents(req.amountCents());
        txn.setStatus(status);
        txn.setGatewayTransactionId(txnId);
        transactionRepository.save(txn);

        order.setStatus(status.equals("SUCCEEDED") ? "REFUNDED" : order.getStatus());
        orderRepository.save(order);
        return new PurchaseResponse(order.getId().toString(), txnId, order.getStatus());
    }
}

