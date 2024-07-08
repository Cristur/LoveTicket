package com.cristianosenterprise.ticket;

import com.cristianosenterprise.event.EventRepository;
import com.cristianosenterprise.security.UserRepository;
import com.cristianosenterprise.security.UserRespository;
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
public class TicketService {

    @Autowired
    private TicketRepository repository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRespository userRespository;

    @Autowired
    private ModelMapper modelMapper;

    private final Logger logger = LoggerFactory.getLogger(TicketService.class);

    public List<TicketRepsonse> findAll() {
        return repository.findAll().stream()
                .map(ticket -> modelMapper.map(ticket, TicketRepsonse.class))
                .collect(Collectors.toList());
    }

    public TicketRepsonse findById(Long id) {
        Ticket ticket = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        return modelMapper.map(ticket, TicketRepsonse.class);
    }

    public TicketRepsonse create(TicketRequest ticketRequest) {
        logger.info("Creating ticket with data: {}", ticketRequest);
        Ticket ticket = modelMapper.map(ticketRequest, Ticket.class);

        ticket.setEvent(eventRepository.findById(ticketRequest.getEventId())
                .orElseThrow(() -> new RuntimeException("Event not found")));

        ticket.setUser(userRespository.findById(ticketRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found")));

        Ticket savedTicket = repository.save(ticket);
        logger.info("Ticket created with id: {}", savedTicket.getId());

        return modelMapper.map(savedTicket, TicketRepsonse.class);
    }

    public TicketRepsonse update(Long id, TicketRequest ticketRequest) {
        Ticket ticket = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        modelMapper.map(ticketRequest, ticket);
        ticket.setId(id);

        ticket.setEvent(eventRepository.findById(ticketRequest.getEventId())
                .orElseThrow(() -> new RuntimeException("Event not found")));

        ticket.setUser(userRespository.findById(ticketRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found")));

        Ticket updatedTicket = repository.save(ticket);

        return modelMapper.map(updatedTicket, TicketRepsonse.class);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
