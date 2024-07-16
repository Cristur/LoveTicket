package com.cristianosenterprise.ticket;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
@Validated
public class TicketController {

    private final TicketService ticketService;

    @GetMapping
    public ResponseEntity<List<TicketResponse>> findAll() {
        List<TicketResponse> ticketResponses = ticketService.findAll();
        return ResponseEntity.ok(ticketResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketResponse> findById(@PathVariable Long id) {
        TicketResponse ticketResponse = ticketService.findById(id);
        return ResponseEntity.ok(ticketResponse);
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<TicketResponse>> findByEventId(@PathVariable Long eventId) {
        List<TicketResponse> ticketResponses = ticketService.findByEventId(eventId);
        return ResponseEntity.ok(ticketResponses);
    }

    @PostMapping
    public ResponseEntity<TicketResponse> create(@RequestBody @Valid TicketRequest ticketRequest) {
        TicketResponse createdTicket = ticketService.create(ticketRequest);
        return new ResponseEntity<>(createdTicket, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TicketResponse> update(@PathVariable Long id, @RequestBody @Valid TicketRequest ticketRequest) {
        TicketResponse updatedTicket = ticketService.update(id, ticketRequest);
        return ResponseEntity.ok(updatedTicket);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ticketService.delete(id);
        return ResponseEntity.noContent().build();
    }
}