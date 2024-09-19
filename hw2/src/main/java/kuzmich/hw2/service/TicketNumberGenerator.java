package kuzmich.hw2.service;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TicketNumberGenerator {

    public TicketNumberGenerator() {
    }

    public String createNewNumber() {
        return "Ticket #" + UUID.randomUUID();
    }
}
