package com.example.ecommerce.service;

import com.example.ecommerce.dto.ProductDTO;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class ProductManagementService implements ProductService {

    private final ProductRepository productRepository;

    public ProductManagementService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> getAll() {
        return productRepository.findAll()
                .stream()
                .map(ProductDTO::fromEntity)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDTO getByUuid(UUID uuid) {
        return productRepository.findByUuid(uuid)
                .map(ProductDTO::fromEntity)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
    }

    @Override
    @Transactional
    public ProductDTO create(ProductDTO productDTO) {
        Product product = new Product(
                UUID.randomUUID(),
                productDTO.name(),
                productDTO.nettoPrice(),
                productDTO.bruttoPrice(),
                productDTO.vat()
        );
        return ProductDTO.fromEntity(productRepository.save(product));
    }

    @Override
    @Transactional
    public void put(UUID uuid, ProductDTO productDTO) {
        Product product = productRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        product.update(productDTO.name(), productDTO.nettoPrice(), productDTO.bruttoPrice(), productDTO.vat());
        ProductDTO.fromEntity(productRepository.save(product));
    }

    @Override
    @Transactional
    public void delete(UUID uuid) {
        if (!productRepository.existsByUuid(uuid)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
        productRepository.deleteByUuid(uuid);
    }
}
