package com.brunodias.product_service.services;
import com.brunodias.product_service.dtos.ProductRequest;
import com.brunodias.product_service.dtos.ProductResponse;
import com.brunodias.product_service.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest productRequest) {
        return null;
    }

    public List<ProductResponse> getAllProducts() {
    return null;
    }

}
