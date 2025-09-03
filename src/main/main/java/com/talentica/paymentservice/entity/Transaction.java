package com.talentica.paymentservice.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String transactionId; // From Authorize.Net
    private Double amount;
    private String type; // PURCHASE, REFUND, AUTHORIZE, CAPTURE, CANCEL
    private String status; // SUCCESS, FAILED, etc.
    private LocalDateTime transactionDate;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    // Getters and Setters
}
