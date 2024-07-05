package com.cristianosenterprise.venue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VenueRequest {
    private String name;
    private String address;
    private int capacity;
    private String description;
}
