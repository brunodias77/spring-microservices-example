package com.brunodias.order_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brunodias.order_service.models.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
