package com.cristianosenterprise.artist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArtistRequest {
    private String name;
    private String genre;
    private String bio;
}
