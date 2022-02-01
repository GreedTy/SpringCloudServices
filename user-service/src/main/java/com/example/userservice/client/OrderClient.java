package com.example.userservice.client;

import com.example.userservice.vo.ResponseOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "ORDER-SERVICE", url = "127.0.0.1:8000")
public interface OrderClient {

    @GetMapping("/order-service/{userId}/orders")
    List<ResponseOrder> getOrders (@PathVariable("userId") String userId);
}
