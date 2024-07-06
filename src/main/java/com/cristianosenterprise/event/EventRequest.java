package com.cristianosenterprise.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventRequest {
    private String name;
    private String description;
    private LocalDateTime eventDate;
    private Double price;
    private Long artistId;
    private Long venueId;
    private Long categoryId;
    private MultipartFile imgFile;
}
