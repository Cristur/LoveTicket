package com.cristianosenterprise.artist;

import lombok.Data;

@Data
public class ArtistResponse {
    private Long id;
    private String name;
    private String genre;
    private String bio;
}
