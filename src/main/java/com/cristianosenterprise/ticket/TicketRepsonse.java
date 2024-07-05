package com.cristianosenterprise.ticket;

import lombok.Data;

@Data
public class TicketRepsonse {
    private Long id;
    private String ticketNumber;
    private Double price;
    private boolean isSold;
    private Long eventId;
    private Long userId;
}
