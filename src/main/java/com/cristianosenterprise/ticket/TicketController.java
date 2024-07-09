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
    public ResponseEntity<List<TicketRepsonse>> findAll() {
        List<TicketRepsonse> ticketResponses = ticketService.findAll();
        return ResponseEntity.ok(ticketResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketRepsonse> findById(@PathVariable Long id) {
        TicketRepsonse ticketResponse = ticketService.findById(id);
        return ResponseEntity.ok(ticketResponse);
    }

    @PostMapping
    public ResponseEntity<TicketRepsonse> create(@RequestBody @Valid TicketRequest ticketRequest) {
        TicketRepsonse createdTicket = ticketService.create(ticketRequest);
        return new ResponseEntity<>(createdTicket, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TicketRepsonse> update(@PathVariable Long id, @RequestBody @Valid TicketRequest ticketRequest) {
        TicketRepsonse updatedTicket = ticketService.update(id, ticketRequest);
        return ResponseEntity.ok(updatedTicket);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ticketService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
