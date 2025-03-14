package com.example.ecommerce.service;

import com.example.ecommerce.dto.ProductDTO;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    List<ProductDTO> getAll();
    ProductDTO getByUuid(UUID uuid);
    ProductDTO create(ProductDTO productDTO);
    void put(UUID uuid, ProductDTO productDTO);
    void delete(UUID uuid);
}
