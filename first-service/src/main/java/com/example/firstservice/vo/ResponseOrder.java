package com.example.firstservice.vo;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseOrder {
    private String productId;
    private Integer qty;
    private Integer stock;
    private Integer unitPrice;
    private Date createAt;
    private String orderId;
}
