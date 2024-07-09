package com.cristianosenterprise.mapper;

import com.cristianosenterprise.event.Event;
import com.cristianosenterprise.event.EventRequest;
import com.cristianosenterprise.event.EventResponse;
import com.cristianosenterprise.ticket.Ticket;
import com.cristianosenterprise.ticket.TicketRepsonse;
import com.cristianosenterprise.ticket.TicketRequest;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);

        // Ignore conflicting fields
        modelMapper.typeMap(EventRequest.class, Event.class).addMappings(mapper -> {
            mapper.skip(Event::setId);
            mapper.skip(Event::setArtist);
            mapper.skip(Event::setCategory);
            mapper.skip(Event::setVenue);
        });
        // Custom mapping from Event to EventResponse
        modelMapper.addMappings(new PropertyMap<Event, EventResponse>() {
            @Override
            protected void configure() {
                map().setArtistId(source.getArtist().getId());
                map().setCategoryId(source.getCategory().getId());
                map().setVenueId(source.getVenue().getId());
            }
        });
        // Mappatura manuale degli ID per evitare problemi di mappatura implicita
        modelMapper.typeMap(TicketRequest.class, Ticket.class).addMappings(mapper ->{
            mapper.skip(Ticket::setEvent); // Ignoriamo la mappatura automatica degli oggetti complessi
            mapper.skip(Ticket::setUser); // Ignoriamo la mappatura automatica degli oggetti complessi
        });

        // Configura la mappatura per TicketRequest -> Ticket
        modelMapper.addMappings(new PropertyMap<Ticket, TicketRepsonse>(){
            @Override
                    protected void configure() {
                map().setEventId(source.getEvent().getId());
                map().setUserId(source.getUser().getId());
            }
        });
        return modelMapper;
    }
}

