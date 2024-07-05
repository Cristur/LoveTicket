package com.cristianosenterprise.order_item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemRequest {
    private int quantity;
    private Double price;
    private Long orderId;
    private Long ticketId;

}
