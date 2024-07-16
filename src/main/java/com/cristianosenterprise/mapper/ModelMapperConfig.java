package com.cristianosenterprise.mapper;

import com.cristianosenterprise.artist.ArtistResponse;
import com.cristianosenterprise.event.Event;
import com.cristianosenterprise.event.EventRequest;
import com.cristianosenterprise.event.EventResponse;
import com.cristianosenterprise.event_category.CategoryResponse;
import com.cristianosenterprise.ticket.Ticket;
import com.cristianosenterprise.ticket.TicketResponse;
import com.cristianosenterprise.ticket.TicketRequest;
import com.cristianosenterprise.venue.VenueResponse;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.stream.Collectors;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);

        // Custom converter for Event to EventResponse
        Converter<Event, EventResponse> eventToEventResponseConverter = context -> {
            Event source = context.getSource();
            EventResponse destination = new EventResponse();
            destination.setId(source.getId());
            destination.setName(source.getName());
            destination.setDescription(source.getDescription());
            destination.setEventDate(source.getEventDate());
            destination.setImg(source.getImg());

            if (source.getArtist() != null) {
                ArtistResponse artistResponse = new ArtistResponse();
                artistResponse.setId(source.getArtist().getId());
                artistResponse.setName(source.getArtist().getName());
                artistResponse.setGenre(source.getArtist().getGenre());
                artistResponse.setBio(source.getArtist().getBio());
                artistResponse.setImg(source.getArtist().getImg());
                // Assicurati che questo non crei un ciclo infinito
                artistResponse.setEvents(null);
                destination.setArtist(artistResponse);
            }

            if (source.getCategory() != null) {
                CategoryResponse categoryResponse = new CategoryResponse();
                categoryResponse.setId(source.getCategory().getId());
                categoryResponse.setName(source.getCategory().getName());
                destination.setCategory(categoryResponse);
            }

            if (source.getVenue() != null) {
                VenueResponse venueResponse = new VenueResponse();
                venueResponse.setId(source.getVenue().getId());
                venueResponse.setName(source.getVenue().getName());
                venueResponse.setAddress(source.getVenue().getAddress());
                venueResponse.setCapacity(source.getVenue().getCapacity());
                venueResponse.setDescription(source.getVenue().getDescription());
                destination.setVenue(venueResponse);
            }

            if (source.getTickets() != null) {
                destination.setTicketIds(source.getTickets().stream().map(Ticket::getId).collect(Collectors.toList()));
            }

            return destination;
        };

        // Add the custom converter to the modelMapper
        modelMapper.addConverter(eventToEventResponseConverter, Event.class, EventResponse.class);

        // Custom mapping from EventRequest to Event
        modelMapper.typeMap(EventRequest.class, Event.class).addMappings(mapper -> {
            mapper.skip(Event::setId);
            mapper.skip(Event::setArtist);
            mapper.skip(Event::setCategory);
            mapper.skip(Event::setVenue);
        });

        // Custom converter for Ticket to TicketResponse
        Converter<Ticket, TicketResponse> ticketToTicketResponseConverter = context -> {
            Ticket source = context.getSource();
            TicketResponse destination = new TicketResponse();
            destination.setId(source.getId());
            destination.setTicketNumber(source.getTicketNumber());
            destination.setPrice(source.getPrice());
            destination.setSold(source.isSold());

            if (source.getEvent() != null) {
                EventResponse eventResponse = modelMapper.map(source.getEvent(), EventResponse.class);
                destination.setEvent(eventResponse);
            }

            if (source.getUser() != null) {
                destination.setUserId(source.getUser().getId());
            }

            return destination;
        };

        // Add the custom converter to the modelMapper
        modelMapper.addConverter(ticketToTicketResponseConverter, Ticket.class, TicketResponse.class);

        // Mappatura manuale degli ID per evitare problemi di mappatura implicita
        modelMapper.typeMap(TicketRequest.class, Ticket.class).addMappings(mapper -> {
            mapper.skip(Ticket::setEvent); // Ignoriamo la mappatura automatica degli oggetti complessi
            mapper.skip(Ticket::setUser); // Ignoriamo la mappatura automatica degli oggetti complessi
        });

        return modelMapper;
    }
}