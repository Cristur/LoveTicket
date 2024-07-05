package com.cristianosenterprise.order_item;

import lombok.Data;

@Data
public class OrderItemResponse {
    private Long id;
    private int quantity;
    private Double price;
    private Long orderId;
    private Long ticketId;
}
