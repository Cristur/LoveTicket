package com.cristianosenterprise.event;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
public class EventResponse {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime eventDate;
    private Double price;
    private Long artistId;
    private Long venueId;
    private Long categoryId;
    private MultipartFile imgFile;
}
