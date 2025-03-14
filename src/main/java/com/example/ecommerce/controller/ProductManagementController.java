package com.example.ecommerce.controller;

import com.example.ecommerce.dto.ProductDTO;
import com.example.ecommerce.dto.Views;
import com.example.ecommerce.service.ProductManagementService;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductManagementController {

    private final ProductManagementService productManagementService;

    public ProductManagementController(ProductManagementService productManagementService) {
        this.productManagementService = productManagementService;
    }

    @GetMapping
    @JsonView(Views.Public.class)
    public ResponseEntity<List<ProductDTO>> getAll() {
        List<ProductDTO> products = productManagementService.getAll();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{uuid}")
    @JsonView(Views.Extended.class)
    public ResponseEntity<ProductDTO> getByUuid(@PathVariable UUID uuid) {
        return ResponseEntity.ok(productManagementService.getByUuid(uuid));
    }

    @PostMapping
    public ResponseEntity<ProductDTO> create(@RequestBody @Valid ProductDTO productDTO) {
        ProductDTO createdProduct = productManagementService.create(productDTO);

        URI location = URI.create(String.format("/api/products/%s", createdProduct.uuid()));
        return ResponseEntity.created(location).body(createdProduct);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<Void> update(@PathVariable UUID uuid, @RequestBody @Valid ProductDTO productDTO) {
        productManagementService.put(uuid, productDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        productManagementService.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}
