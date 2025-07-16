package com.brunodias.stock_service.services;

import com.brunodias.stock_service.repositories.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockService {

    private final StockRepository stockRepository;

    public boolean isInStock(String skuCode, Integer quantity) {
        log.info("Início -- Solicitação recebida para verificar estoque do skuCode {}, com quantidade {}", skuCode, quantity);
        boolean isInStock = stockRepository.existsBySkuCodeAndQuantityIsGreaterThanEqual(skuCode, quantity);
        log.info("Fim -- Produto com skuCode {}, e quantidade {}, está em estoque: {}", skuCode, quantity, isInStock);
        return isInStock;
    }

}
