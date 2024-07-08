package com.cristianosenterprise.artist;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.cristianosenterprise.event.Event;
import com.cristianosenterprise.event.EventRepository;
import com.cristianosenterprise.event.EventResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class ArtistService {

    @Autowired
    private ArtistRepository repository;

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private ModelMapper modelMapper;

    private final Logger logger = LoggerFactory.getLogger(ArtistService.class);

    public List<ArtistResponse> findAll() {
        return repository.findAll().stream()
                .map(artist -> {
                    ArtistResponse artistResponse = modelMapper.map(artist, ArtistResponse.class);
                    if (artist.getEvents() != null) {
                        List<EventResponse> eventResponses = artist.getEvents().stream()
                                .map(event -> modelMapper.map(event, EventResponse.class))
                                .collect(Collectors.toList());
                        artistResponse.setEvents(eventResponses);
                    }
                    return artistResponse;
                })
                .collect(Collectors.toList());
    }

    public ArtistResponse findById(Long id) {
        Artist artist = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artist not found"));

        ArtistResponse artistResponse = modelMapper.map(artist, ArtistResponse.class);
        if (artist.getEvents() != null) {
            List<EventResponse> eventResponses = artist.getEvents().stream()
                    .map(event -> modelMapper.map(event, EventResponse.class))
                    .collect(Collectors.toList());
            artistResponse.setEvents(eventResponses);
        }

        return artistResponse;
    }

    public ArtistResponse create(@Valid ArtistRequest artistRequest, MultipartFile image) throws IOException {
        Artist artist = modelMapper.map(artistRequest, Artist.class);

        if (image != null && !image.isEmpty()) {
            Map<String, Object> uploadResult = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());
            String imageUrl = (String) uploadResult.get("url");
            artist.setImg(imageUrl);
        }
        return modelMapper.map(repository.save(artist), ArtistResponse.class);
    }

    public ArtistResponse update(Long id, ArtistRequest artistRequest, MultipartFile image) throws IOException {
        Artist artist = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artist not found"));

        modelMapper.map(artistRequest, artist);
        artist.setId(id);

        if (image != null && !image.isEmpty()) {
            if (artist.getImg() != null) {
                cloudinary.uploader().destroy(artist.getImg(), ObjectUtils.emptyMap());
            }
            Map<String, Object> uploadResult = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());
            String logoUrl = (String) uploadResult.get("url");
            artist.setImg(logoUrl);
        }

        Artist updatedArtist = repository.save(artist);

        ArtistResponse artistResponse = modelMapper.map(updatedArtist, ArtistResponse.class);
        if (updatedArtist.getEvents() != null) {
            List<EventResponse> eventResponses = updatedArtist.getEvents().stream()
                    .map(event -> modelMapper.map(event, EventResponse.class))
                    .collect(Collectors.toList());
            artistResponse.setEvents(eventResponses);
        }

        return artistResponse;
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
