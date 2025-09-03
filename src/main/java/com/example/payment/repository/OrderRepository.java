package com.example.payment.repository;

import com.example.payment.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    Optional<Order> findByExternalOrderId(String externalOrderId);
}

