package com.cristianosenterprise.event;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.cristianosenterprise.artist.ArtistRepository;
import com.cristianosenterprise.event_category.CategoryRepository;
import com.cristianosenterprise.venue.VenueRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Validated
@RequiredArgsConstructor
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private VenueRepository venueRepository;

    public List<EventResponse> findAll() {
        return eventRepository.findAll().stream()
                .map(event -> modelMapper.map(event, EventResponse.class))
                .collect(Collectors.toList());
    }

    public EventResponse findById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        return modelMapper.map(event, EventResponse.class);
    }

    public EventResponse create(@Valid EventRequest eventRequest, MultipartFile image) throws IOException {
        Event event = modelMapper.map(eventRequest, Event.class);

        event.setArtist(artistRepository.findById(eventRequest.getArtistId())
                .orElseThrow(() -> new RuntimeException("Artist not found")));
        event.setCategory(categoryRepository.findById(eventRequest.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found")));
        event.setVenue(venueRepository.findById(eventRequest.getVenueId())
                .orElseThrow(() -> new RuntimeException("Venue not found")));

        if (image != null && !image.isEmpty()) {
            Map<String, Object> uploadResult = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());
            String imageUrl = (String) uploadResult.get("url");
            event.setImg(imageUrl);
        }

        Event savedEvent = eventRepository.save(event);
        return modelMapper.map(savedEvent, EventResponse.class);
    }

    public EventResponse update(Long id, @Valid EventRequest eventRequest, MultipartFile image) throws IOException {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        modelMapper.map(eventRequest, event);
        event.setId(id);

        event.setArtist(artistRepository.findById(eventRequest.getArtistId())
                .orElseThrow(() -> new RuntimeException("Artist not found")));
        event.setCategory(categoryRepository.findById(eventRequest.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found")));
        event.setVenue(venueRepository.findById(eventRequest.getVenueId())
                .orElseThrow(() -> new RuntimeException("Venue not found")));

        if (image != null && !image.isEmpty()) {
            Map<String, Object> uploadResult = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());
            String imageUrl = (String) uploadResult.get("url");
            event.setImg(imageUrl);
        }

        Event savedEvent = eventRepository.save(event);
        return modelMapper.map(savedEvent, EventResponse.class);
    }

    public void delete(Long id) {
        eventRepository.deleteById(id);
    }
}
