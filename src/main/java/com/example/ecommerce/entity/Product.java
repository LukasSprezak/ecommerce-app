package com.example.ecommerce.entity;

import com.example.ecommerce.enums.Vat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotNull
    private UUID uuid;

    @NotNull
    private String name;

    @Column(scale = 2, precision = 12)
    @Digits(integer = 10, fraction = 2)
    @Min(0)
    @NotNull
    private BigDecimal nettoPrice;

    @Column(scale = 2, precision = 12)
    @Digits(integer = 10, fraction = 2)
    @Min(0)
    @NotNull
    private BigDecimal bruttoPrice;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Vat vat;

    protected Product() {}

    public Product(UUID uuid, String name, BigDecimal nettoPrice, BigDecimal bruttoPrice, Vat vat) {
        this.uuid = uuid;
        this.name = name;
        this.nettoPrice = nettoPrice;
        this.bruttoPrice = bruttoPrice;
        this.vat = vat;
    }

    public Long getId() {
        return id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getNettoPrice() {
        return nettoPrice;
    }

    public BigDecimal getBruttoPrice() {
        return bruttoPrice;
    }

    public Vat getVat() {
        return vat;
    }

    public void update(String name, BigDecimal nettoPrice, BigDecimal bruttoPrice, Vat vat) {
        this.name = name;
        this.nettoPrice = nettoPrice;
        this.bruttoPrice = bruttoPrice;
        this.vat = vat;
    }
}
