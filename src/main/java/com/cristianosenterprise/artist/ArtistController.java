package com.cristianosenterprise.artist;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/artists")
@RequiredArgsConstructor
@Validated
public class ArtistController {

    private final ArtistService artistService;

    @GetMapping
    public ResponseEntity<List<ArtistResponse>> findAll() {
        List<ArtistResponse> artistResponses = artistService.findAll();
        return ResponseEntity.ok(artistResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtistResponse> findById(@PathVariable Long id) {
        ArtistResponse artistResponse = artistService.findById(id);
        return ResponseEntity.ok(artistResponse);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ArtistResponse> createArtist(
            @RequestPart("artist") @Valid String artistJson,
            @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ArtistRequest artistRequest = objectMapper.readValue(artistJson, ArtistRequest.class);
        ArtistResponse createdArtist = artistService.create(artistRequest, image);
        return new ResponseEntity<>(createdArtist, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ArtistResponse> update(@PathVariable Long id,
                                                 @RequestPart("artist") @Valid String artistJson,
                                                 @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ArtistRequest artistRequest = objectMapper.readValue(artistJson, ArtistRequest.class);
        ArtistResponse artistResponse = artistService.update(id, artistRequest, image);
        return ResponseEntity.ok(artistResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        artistService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
