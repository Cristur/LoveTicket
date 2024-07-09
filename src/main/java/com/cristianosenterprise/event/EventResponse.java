package com.cristianosenterprise.event;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventResponse {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime eventDate;
    private Long artistId;
    private Long venueId;
    private Long categoryId;
    private String img;
}
