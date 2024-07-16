package com.cristianosenterprise.ticket;

import com.cristianosenterprise.event.EventRepository;
import com.cristianosenterprise.security.UserRespository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final EventRepository eventRepository;
    private final UserRespository userRepository;
    private final ModelMapper modelMapper;

    public List<TicketResponse> findAll() {
        return ticketRepository.findAll().stream()
                .map(ticket -> modelMapper.map(ticket, TicketResponse.class))
                .collect(Collectors.toList());
    }

    public TicketResponse findById(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
        return modelMapper.map(ticket, TicketResponse.class);
    }

    public TicketResponse create(@Valid TicketRequest ticketRequest) {
        Ticket ticket = modelMapper.map(ticketRequest, Ticket.class);

        // Set event and user
        if (ticketRequest.getEventId() != null) {
            ticket.setEvent(eventRepository.findById(ticketRequest.getEventId())
                    .orElseThrow(() -> new RuntimeException("Event not found")));
        }

        if (ticketRequest.getUserId() != null) {
            ticket.setUser(userRepository.findById(ticketRequest.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found")));
        }

        Ticket savedTicket = ticketRepository.save(ticket);
        return modelMapper.map(savedTicket, TicketResponse.class);
    }

    public TicketResponse update(Long id, @Valid TicketRequest ticketRequest) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        modelMapper.map(ticketRequest, ticket);

        // Set event and user
        if (ticketRequest.getEventId() != null) {
            ticket.setEvent(eventRepository.findById(ticketRequest.getEventId())
                    .orElseThrow(() -> new RuntimeException("Event not found")));
        }

        if (ticketRequest.getUserId() != null) {
            ticket.setUser(userRepository.findById(ticketRequest.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found")));
        }

        Ticket savedTicket = ticketRepository.save(ticket);
        return modelMapper.map(savedTicket, TicketResponse.class);
    }

    public void delete(Long id) {
        ticketRepository.deleteById(id);
    }

    public List<TicketResponse> findByEventId(Long eventId) {
        return ticketRepository.findByEvent_Id(eventId).stream()
                .map(ticket -> modelMapper.map(ticket, TicketResponse.class))
                .collect(Collectors.toList());
    }
}
