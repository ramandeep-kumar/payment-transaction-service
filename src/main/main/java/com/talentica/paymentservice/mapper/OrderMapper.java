package com.talentica.paymentservice.mapper;

import com.talentica.paymentservice.entity.Order;
import com.talentica.paymentservice.model.PaymentRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class OrderMapper {
    public Order toOrder(PaymentRequest request) {
        Order order = new Order();
        order.setAmount(request.getAmount());
        order.setCurrency(request.getCurrency());
        order.setStatus("PENDING");
        order.setCreatedAt(LocalDateTime.now());
        return order;
    }
}
