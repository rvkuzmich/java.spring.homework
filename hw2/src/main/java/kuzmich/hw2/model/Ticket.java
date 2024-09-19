package kuzmich.hw2.model;

import java.time.LocalDateTime;

public class Ticket {
    String number;
    LocalDateTime createdAt;
    String name;
    String lastname;

    public Ticket(String number, String name, String lastname) {
        this.number = number;
        this.createdAt = LocalDateTime.now();
        this.name = name;
        this.lastname = lastname;
    }

    public String getNumber() {
        return number;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }
}
