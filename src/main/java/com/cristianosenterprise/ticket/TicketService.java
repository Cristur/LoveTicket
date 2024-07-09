package com.cristianosenterprise.ticket;

import com.cristianosenterprise.event.EventRepository;
import com.cristianosenterprise.security.UserRespository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
@RequiredArgsConstructor
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRespository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<TicketRepsonse> findAll() {
        return ticketRepository.findAll().stream()
                .map(ticket -> modelMapper.map(ticket, TicketRepsonse.class))
                .collect(Collectors.toList());
    }

    public TicketRepsonse findById(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
        return modelMapper.map(ticket, TicketRepsonse.class);
    }

    public TicketRepsonse create(@Valid TicketRequest ticketRequest) {
        Ticket ticket = modelMapper.map(ticketRequest, Ticket.class);

        ticket.setEvent(eventRepository.findById(ticketRequest.getEventId())
                .orElseThrow(() -> new RuntimeException("Event not found")));
        ticket.setUser(userRepository.findById(ticketRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found")));

        Ticket savedTicket = ticketRepository.save(ticket);
        return modelMapper.map(savedTicket, TicketRepsonse.class);
    }

    public TicketRepsonse update(Long id, @Valid TicketRequest ticketRequest) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        modelMapper.map(ticketRequest, ticket);

        ticket.setEvent(eventRepository.findById(ticketRequest.getEventId())
                .orElseThrow(() -> new RuntimeException("Event not found")));
        ticket.setUser(userRepository.findById(ticketRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found")));

        Ticket savedTicket = ticketRepository.save(ticket);
        return modelMapper.map(savedTicket, TicketRepsonse.class);
    }

    public void delete(Long id) {
        ticketRepository.deleteById(id);
    }
}
