package com.cristianosenterprise.event;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class EventResponse {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime eventDate;
    private Long artistId;
    private Long venueId;
    private Long categoryId;
    private String img;
    private List<Long> ticketIds;
}
