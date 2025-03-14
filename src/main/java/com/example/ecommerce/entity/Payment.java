package com.example.ecommerce.entity;

import com.example.ecommerce.enums.PaymentStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private UUID uuid;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    @Column(nullable = false)
    private Instant paymentDate;

    public Long getId() {
        return id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public User getUser() {
        return user;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public Instant getPaymentDate() {
        return paymentDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public void setPaymentDate(Instant paymentDate) {
        this.paymentDate = paymentDate;
    }
}
