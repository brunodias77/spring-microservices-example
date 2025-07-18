package com.brunodias.order_service.clients;

import jakarta.persistence.ForeignKey;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "stock", url = "http://localhost:8081")
public interface StockClient {

    @RequestMapping(method = RequestMethod.GET, value = "/api/stock")
    boolean isInStock(@RequestParam String skuCode, @RequestParam Integer quantity);

}

