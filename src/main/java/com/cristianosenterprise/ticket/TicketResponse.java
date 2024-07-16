package com.cristianosenterprise.ticket;

import com.cristianosenterprise.event.EventResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketResponse {
    private Long id;
    private String ticketNumber;
    private Double price;
    private boolean isSold;
    private EventResponse event;
    private Long userId;
}