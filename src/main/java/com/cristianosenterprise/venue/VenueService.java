package com.cristianosenterprise.venue;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
@RequiredArgsConstructor
public class VenueService {

    @Autowired
    private VenueRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    private final Logger logger = LoggerFactory.getLogger(VenueService.class);

    public List<VenueResponse> findAll() {
        return repository.findAll().stream()
                .map(venue -> modelMapper.map(venue, VenueResponse.class))
                .collect(Collectors.toList());
    }

    public VenueResponse findById(Long id) {
        Venue venue = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venue not found"));

        return modelMapper.map(venue, VenueResponse.class);
    }

    public VenueResponse create(VenueRequest venueRequest) {
        logger.info("Creating venue with data: {}", venueRequest);
        Venue venue = modelMapper.map(venueRequest, Venue.class);

        Venue savedVenue = repository.save(venue);
        logger.info("Venue created with id: {}", savedVenue.getId());

        return modelMapper.map(savedVenue, VenueResponse.class);
    }

    public VenueResponse update(Long id, VenueRequest venueRequest) {
        Venue venue = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venue not found"));

        modelMapper.map(venueRequest, venue);
        venue.setId(id);

        Venue updatedVenue = repository.save(venue);

        return modelMapper.map(updatedVenue, VenueResponse.class);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
