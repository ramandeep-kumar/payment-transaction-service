package com.talentica.paymentservice.repository;

import com.talentica.paymentservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    Optional<Order> findByExternalOrderId(String externalOrderId);
}

