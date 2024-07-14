package com.cristianosenterprise.event;

import com.cristianosenterprise.artist.Artist;
import com.cristianosenterprise.event_category.Category;
import com.cristianosenterprise.ticket.Ticket;
import com.cristianosenterprise.venue.Venue;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import lombok.Data;


import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private LocalDateTime eventDate;
    @OneToMany(mappedBy = "event")
    private List<Ticket> tickets;
    @ManyToOne
    @JoinColumn(name = "artist_id")
    @JsonBackReference
    private Artist artist;
    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonBackReference
    private Category category;
    @ManyToOne
    @JoinColumn(name = "venue_id")
    @JsonBackReference
    private Venue venue;
    private String img;
}
