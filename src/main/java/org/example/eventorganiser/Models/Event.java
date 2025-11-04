package org.example.eventorganiser.Models;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.List;


@Entity
public class Event {
    @Id
    @GeneratedValue
    private int eventId;

    private String eventName;

    private Date eventDate;

    private String event_location;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<EventGuests> event_guests;
}
