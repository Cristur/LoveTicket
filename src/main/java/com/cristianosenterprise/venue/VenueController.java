package com.cristianosenterprise.venue;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/venues")
@RequiredArgsConstructor
@Validated
public class VenueController {

    @Autowired
    private final VenueService venueService;

    @GetMapping
    public ResponseEntity<List<VenueResponse>> findAll() {
        List<VenueResponse> venueResponses = venueService.findAll();
        return ResponseEntity.ok(venueResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VenueResponse> findById(@PathVariable Long id) {
        VenueResponse venueResponse = venueService.findById(id);
        return ResponseEntity.ok(venueResponse);
    }

    @PostMapping
    public ResponseEntity<VenueResponse> createVenue(@RequestBody @Valid VenueRequest venueRequest) {
        VenueResponse createdVenue = venueService.create(venueRequest);
        return new ResponseEntity<>(createdVenue, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VenueResponse> update(@PathVariable Long id,
                                                @RequestBody @Valid VenueRequest venueRequest) {
        VenueResponse venueResponse = venueService.update(id, venueRequest);
        return ResponseEntity.ok(venueResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        venueService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
