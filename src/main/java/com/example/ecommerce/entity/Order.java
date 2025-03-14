package com.example.ecommerce.entity;

import com.example.ecommerce.enums.OrderStatus;
import com.example.ecommerce.enums.PaymentStatus;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private UUID uuid;

    @Column(scale = 2, precision = 12, nullable = false)
    @Digits(integer = 10, fraction = 2)
    @Min(0)
    private BigDecimal nettoPrice;

    @Column(scale = 2, precision = 12, nullable = false)
    @Digits(integer = 10, fraction = 2)
    @Min(0)
    private BigDecimal bruttoPrice;

    @Column(scale = 2, precision = 12, nullable = false)
    @Digits(integer = 10, fraction = 2)
    @Min(0)
    private BigDecimal amountToPay;

    @Nullable
    @ManyToOne
    private Discount discount;

    @Nullable
    @Lob
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus orderStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;

    @NotNull
    private Instant orderTime;

    @Nullable
    private Instant issuedTime;

    @Nullable
    private Instant deliveredTime;

    @Column(nullable = false)
    private Boolean isPaid;

    @NotNull
    @Size(min = 1)
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    @ManyToOne
    private User user;

    public Long getId() {
        return id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public BigDecimal getNettoPrice() {
        return nettoPrice;
    }

    public BigDecimal getBruttoPrice() {
        return bruttoPrice;
    }

    public BigDecimal getAmountToPay() {
        return amountToPay;
    }

    @Nullable
    public Discount getDiscount() {
        return discount;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public Instant getOrderTime() {
        return orderTime;
    }

    @Nullable
    public Instant getIssuedTime() {
        return issuedTime;
    }

    @Nullable
    public Instant getDeliveredTime() {
        return deliveredTime;
    }

    public Boolean getPaid() {
        return isPaid;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public User getUser() {
        return user;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setNettoPrice(BigDecimal nettoPrice) {
        this.nettoPrice = nettoPrice;
    }

    public void setBruttoPrice(BigDecimal bruttoPrice) {
        this.bruttoPrice = bruttoPrice;
    }

    public void setAmountToPay(BigDecimal amountToPay) {
        this.amountToPay = amountToPay;
    }

    public void setDiscount(@Nullable Discount discount) {
        this.discount = discount;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public void setOrderTime(Instant orderTime) {
        this.orderTime = orderTime;
    }

    public void setIssuedTime(@Nullable Instant issuedTime) {
        this.issuedTime = issuedTime;
    }

    public void setDeliveredTime(@Nullable Instant deliveredTime) {
        this.deliveredTime = deliveredTime;
    }

    public void setPaid(Boolean paid) {
        isPaid = paid;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
