package com.cristianosenterprise.ticket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketRepsonse {
    private Long id;
    private String ticketNumber;
    private Double price;
    private boolean isSold;
    private Long eventId;
    private Long userId;
}
