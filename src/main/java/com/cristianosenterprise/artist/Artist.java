package com.cristianosenterprise.artist;

import com.cristianosenterprise.event.Event;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String genre;
    private String bio;
    @OneToMany(mappedBy = "artist")
    @JsonManagedReference
    private List<Event> events;
    private String img;
}
