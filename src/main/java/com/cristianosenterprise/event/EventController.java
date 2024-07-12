package com.cristianosenterprise.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
@Validated
public class EventController {

    @Autowired
    private final EventService eventService;

    @GetMapping
    public ResponseEntity<List<EventResponse>> findAll() {
        List<EventResponse> eventResponses = eventService.findAll();
        return ResponseEntity.ok(eventResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> findById(@PathVariable Long id) {
        EventResponse eventResponse = eventService.findById(id);
        return ResponseEntity.ok(eventResponse);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<EventResponse>> findByCategory(@PathVariable Long categoryId) {
        List<EventResponse> eventResponses = eventService.findByCategory(categoryId);
        return ResponseEntity.ok(eventResponses);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<EventResponse> createEvent(
            @RequestPart("event") @Valid String eventJson,
            @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        EventRequest eventRequest = objectMapper.readValue(eventJson, EventRequest.class);
        EventResponse createdEvent = eventService.create(eventRequest, image);
        return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventResponse> update(@PathVariable Long id,
                                                @Valid @RequestPart("event") String eventJson,
                                                @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        EventRequest eventRequest = objectMapper.readValue(eventJson, EventRequest.class);
        EventResponse eventResponse = eventService.update(id, eventRequest, image);
        return ResponseEntity.ok(eventResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        eventService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
