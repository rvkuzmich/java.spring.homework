package kuzmich.hw2.view;

import kuzmich.hw2.model.Ticket;
import kuzmich.hw2.service.TicketNumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Board {
    @Autowired
    TicketNumberGenerator ticketNumberGenerator;

    public Board(TicketNumberGenerator ticketNumberGenerator) {
        this.ticketNumberGenerator = ticketNumberGenerator;
    }

    public void newTicket(String name, String lastname) {
        Ticket ticket = new Ticket(ticketNumberGenerator.createNewNumber(), name, lastname);

        System.out.printf("%s\nDate %s\nTicket for %s %s\n", ticket.getNumber(), ticket.getCreatedAt(), ticket.getName(), ticket.getLastname());
    }
}
