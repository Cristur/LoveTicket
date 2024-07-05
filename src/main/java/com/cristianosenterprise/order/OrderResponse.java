package com.cristianosenterprise.order;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderResponse {
    private Long id;
    private LocalDateTime orderDate;
    private Double totalAmount;
    private Long userId;
}
