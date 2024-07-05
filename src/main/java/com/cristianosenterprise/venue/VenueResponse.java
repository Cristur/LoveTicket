package com.cristianosenterprise.venue;

import lombok.Data;

@Data
public class VenueResponse {
    private Long id;
    private String name;
    private String address;
    private int capacity;
    private String description;
}
