package com.cristianosenterprise.artist;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.cristianosenterprise.event.EventResponse;
import com.cristianosenterprise.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Validated
public class ArtistService {

    @Autowired
    private ArtistRepository repository;

    @Autowired
    private Cloudinary cloudinary;

    public List<ArtistResponse> findAll() {
        return repository.findAll().stream().map(artist -> {
            ArtistResponse artistResponse = new ArtistResponse();
            BeanUtils.copyProperties(artist, artistResponse);
            //converto gli eventi in response
            List<EventResponse> eventResponses = artist.getEvents().stream()
                    .map(event -> {
                        EventResponse eventResponse = new EventResponse();
                        BeanUtils.copyProperties(event, eventResponse);
                        return eventResponse;
                    }).collect(Collectors.toList());
            if (artist.getImg() != null) {
                try {
                    Map<String, Object> uploadResult = cloudinary.uploader().upload(artist.getImg(), ObjectUtils.emptyMap());
                    String url = (String) uploadResult.get("url");
                    MultipartFile imgFile = convertToMultipartFile(url);
                    artistResponse.setImgFile(imgFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            artistResponse.setEvents(eventResponses);
            return artistResponse;
        }).collect(Collectors.toList());
    }

    private MultipartFile convertToMultipartFile(String url) {
        // Here you need to implement a method to convert the URL to a MultipartFile
        // This is just a placeholder implementation
        try {
            File file = new File(url);
            FileInputStream input = new FileInputStream(file);
            return new CommonsMultipartFile(file.getName(), file.getName(), "image/jpeg", input);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public ArtistResponse findById(Long id) {
        Artist artist = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artist with this ID not found:" + id));
        ArtistResponse artistResponse = new ArtistResponse();
        BeanUtils.copyProperties(artist, artistResponse);
        return artistResponse;
    }

    public ArtistResponse create(@Valid ArtistRequest artistRequest) throws IOException {
        Artist artist = new Artist();
        BeanUtils.copyProperties(artistRequest, artist);

    }
}
