package com.talentica.paymentservice.service.impl;

import com.talentica.paymentservice.entity.Order;
import com.talentica.paymentservice.entity.Transaction;
import com.talentica.paymentservice.enum.PaymentStatus;
import com.talentica.paymentservice.enum.TransactionType;
import com.talentica.paymentservice.exception.PaymentException;
import com.talentica.paymentservice.model.PaymentRequest;
import com.talentica.paymentservice.model.PaymentResponse;
import com.talentica.paymentservice.repository.OrderRepository;
import com.talentica.paymentservice.repository.TransactionRepository;
import com.talentica.paymentservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    // NOTE: Authorize.Net integration code goes here (use official SDK)

    @Override
    public PaymentResponse purchase(PaymentRequest request) {
        // 1-step auth+capture via Authorize.Net
        PaymentResponse response = new PaymentResponse();

        // Create Order
        Order order = new Order();
        order.setAmount(request.getAmount());
        order.setCurrency(request.getCurrency());
        order.setStatus(PaymentStatus.PENDING.name());
        order.setCreatedAt(LocalDateTime.now());
        order = orderRepository.save(order);

        // Create Transaction
        Transaction txn = new Transaction();
        txn.setTransactionId("simulated-purchase-txn");
        txn.setAmount(request.getAmount());
        txn.setType(TransactionType.PURCHASE.name());
        txn.setStatus(PaymentStatus.CAPTURED.name());
        txn.setTransactionDate(LocalDateTime.now());
        txn.setOrder(order);
        transactionRepository.save(txn);

        response.setStatus("SUCCESS");
        response.setTransactionId(txn.getTransactionId());
        response.setMessage("Purchase processed in sandbox.");
        return response;
    }

    @Override
    public PaymentResponse authorize(PaymentRequest request) {
        PaymentResponse response = new PaymentResponse();
        Order order = new Order();
        order.setAmount(request.getAmount());
        order.setCurrency(request.getCurrency());
        order.setStatus(PaymentStatus.PENDING.name());
        order.setCreatedAt(LocalDateTime.now());
        order = orderRepository.save(order);

        Transaction txn = new Transaction();
        txn.setTransactionId("simulated-auth-txn");
        txn.setAmount(request.getAmount());
        txn.setType(TransactionType.AUTHORIZE.name());
        txn.setStatus(PaymentStatus.AUTHORIZED.name());
        txn.setTransactionDate(LocalDateTime.now());
        txn.setOrder(order);
        transactionRepository.save(txn);

        response.setStatus("AUTHORIZED");
        response.setTransactionId(txn.getTransactionId());
        response.setMessage("Payment authorized in sandbox.");
        return response;
    }

    @Override
    public PaymentResponse capture(String transactionId) {
        PaymentResponse response = new PaymentResponse();

        Transaction txn = transactionRepository.findAll()
                .stream().filter(t -> t.getTransactionId().equals(transactionId)).findFirst()
                .orElseThrow(() -> new PaymentException("Transaction not found"));

        txn.setStatus(PaymentStatus.CAPTURED.name());
        txn.setType(TransactionType.CAPTURE.name());
        transactionRepository.save(txn);

        response.setStatus("CAPTURED");
        response.setTransactionId(transactionId);
        response.setMessage("Payment captured in sandbox.");
        return response;
    }

    @Override
    public PaymentResponse cancel(String transactionId) {
        PaymentResponse response = new PaymentResponse();

        Transaction txn = transactionRepository.findAll()
                .stream().filter(t -> t.getTransactionId().equals(transactionId)).findFirst()
                .orElseThrow(() -> new PaymentException("Transaction not found"));

        txn.setStatus(PaymentStatus.CANCELLED.name());
        txn.setType(TransactionType.CANCEL.name());
        transactionRepository.save(txn);

        response.setStatus("CANCELLED");
        response.setTransactionId(transactionId);
        response.setMessage("Payment cancelled in sandbox.");
        return response;
    }

    @Override
    public PaymentResponse refund(String transactionId, Double amount) {
        PaymentResponse response = new PaymentResponse();

        Transaction originalTxn = transactionRepository.findAll()
                .stream().filter(t -> t.getTransactionId().equals(transactionId)).findFirst()
                .orElseThrow(() -> new PaymentException("Transaction not found"));

        Transaction refundTxn = new Transaction();
        refundTxn.setTransactionId("simulated-refund-" + transactionId);
        refundTxn.setAmount(amount);
        refundTxn.setType(TransactionType.REFUND.name());
        refundTxn.setStatus(PaymentStatus.REFUNDED.name());
        refundTxn.setTransactionDate(LocalDateTime.now());
        refundTxn.setOrder(originalTxn.getOrder());
        transactionRepository.save(refundTxn);

        response.setStatus("REFUNDED");
        response.setTransactionId(refundTxn.getTransactionId());
        response.setMessage("Refund processed in sandbox. Amount: " + amount);
        return response;
    }
}
