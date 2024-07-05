package com.cristianosenterprise.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private LocalDateTime orderDate;
    private Double totalAmount;
    private Long userId;
}
