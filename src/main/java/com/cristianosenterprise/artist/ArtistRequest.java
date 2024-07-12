package com.cristianosenterprise.artist;

import com.cristianosenterprise.event.EventResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArtistRequest {
    private String name;
    private String genre;
    private String bio;
    private List<Long> events;
    private MultipartFile img;
}
