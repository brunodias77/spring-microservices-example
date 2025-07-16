package com.brunodias.order_service.services;

import com.brunodias.order_service.clients.StockClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.brunodias.order_service.dtos.OrderRequest;
import com.brunodias.order_service.models.Order;
import com.brunodias.order_service.repositories.OrderRepository;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final StockClient stockClient;

    public void placeOrder(OrderRequest orderRequest) {
        var isProductInStock = stockClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());
        if (isProductInStock) {
            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setPrice(orderRequest.price());
            order.setSkuCode(orderRequest.skuCode());
            order.setQuantity(orderRequest.quantity());
            orderRepository.save(order);
        } else {
            throw new RuntimeException(
                    "Produto com o SkuCode " + orderRequest.skuCode() + "esta com o estoque esgotado");
        }
    }
}
