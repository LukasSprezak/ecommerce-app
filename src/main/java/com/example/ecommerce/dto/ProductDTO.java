package com.example.ecommerce.dto;

import com.example.ecommerce.entity.Product;
import com.example.ecommerce.enums.Vat;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductDTO(
        @JsonView(Views.Public.class) Long id,
        @JsonView(Views.Public.class) UUID uuid,
        @JsonView(Views.Public.class) @NotNull String name,
        @JsonView(Views.Extended.class) @NotNull BigDecimal nettoPrice,
        @JsonView(Views.Extended.class) @NotNull BigDecimal bruttoPrice,
        @JsonView(Views.Extended.class) @NotNull Vat vat
) {
    public static ProductDTO fromEntity(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getUuid(),
                product.getName(),
                product.getNettoPrice(),
                product.getBruttoPrice(),
                product.getVat()
        );
    }
}
