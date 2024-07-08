package com.cristianosenterprise.mapper;

import com.cristianosenterprise.event.Event;
import com.cristianosenterprise.event.EventResponse;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Configura il mapping tra EventResponse e Event
        modelMapper.typeMap(EventResponse.class, Event.class);

        return modelMapper;
    }
}
