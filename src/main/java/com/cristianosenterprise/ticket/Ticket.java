package com.cristianosenterprise.ticket;

import com.cristianosenterprise.event.Event;
import com.cristianosenterprise.security.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Ticket {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String ticketNumber;
        private Double price;
        private boolean isSold;

        @ManyToOne
        @JoinColumn(name = "event_id")
        private Event event;

        @ManyToOne
        @JoinColumn(name = "user_id")
        private User user;

}