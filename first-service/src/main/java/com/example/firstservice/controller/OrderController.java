package com.example.firstservice.controller;

import com.example.firstservice.dto.OrderDto;
import com.example.firstservice.entity.OrderEntity;
import com.example.firstservice.messagequeue.KafkaProducer;
import com.example.firstservice.messagequeue.OrderProducer;
import com.example.firstservice.service.OrderService;
import com.example.firstservice.vo.RequestOrder;
import com.example.firstservice.vo.ResponseOrder;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/")
@Slf4j
public class OrderController {

    private OrderService orderService;
    private Environment environment;
    private KafkaProducer kafkaProducer;
    private OrderProducer orderProducer;

    public OrderController(OrderService orderService, Environment environment,
                           KafkaProducer kafkaProducer, OrderProducer orderProducer) {
        this.orderService = orderService;
        this.environment = environment;
        this.kafkaProducer = kafkaProducer;
        this.orderProducer = orderProducer;
    }

    @GetMapping("/health_check")
    public String status() {
        return "hello";
    }

    @PostMapping("/{userId}/orders")
    public ResponseEntity<ResponseOrder> createOrder(@PathVariable("userId") String userId,
                                                     @RequestBody RequestOrder orderDetails) {
        log.info("Before add orders microservice");
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        /* jpa */
        //OrderDto createDto = orderService.createOrder(orderDto);
        //ResponseOrder returnValue = mapper.map(createDto, ResponseOrder.class);

        /* kafka send */
        OrderDto orderDto = mapper.map(orderDetails, OrderDto.class);
        orderDto.setUserId(userId);
        orderDto.setOrderId(UUID.randomUUID().toString());
        orderDto.setTotalPrice(orderDetails.getQty() * orderDetails.getUnitPrice());

        kafkaProducer.send("example-catalog-topic", orderDto);
        orderProducer.send("orders", orderDto);

        ResponseOrder returnValue = mapper.map(orderDto, ResponseOrder.class);
        log.info("After add orders microservice");
        return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<ResponseOrder>> getOrder(@PathVariable("userId") String userId) {
        log.info("Before process orders microservice");
        Iterable<OrderEntity> orderList = orderService.getOrdersByUserId(userId);
        List<ResponseOrder> result = new ArrayList<>();
        orderList.forEach(v -> {
            result.add(new ModelMapper().map(v, ResponseOrder.class));
        });
        log.info("After process orders microservice");
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}
