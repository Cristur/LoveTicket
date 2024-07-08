package com.cristianosenterprise.artist;

import com.cristianosenterprise.event.EventResponse;
import lombok.Data;

import java.util.List;

@Data
public class ArtistResponse {
    private Long id;
    private String name;
    private String genre;
    private String bio;
    private List<EventResponse> events;
    private String img;
}
