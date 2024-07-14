package com.cristianosenterprise.event;


import com.cristianosenterprise.artist.ArtistResponse;
import com.cristianosenterprise.event_category.CategoryResponse;
import com.cristianosenterprise.venue.VenueResponse;
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
    private ArtistResponse artist;
    private VenueResponse venue;
    private CategoryResponse category;
    private String img;
    private List<Long> ticketIds;
}
