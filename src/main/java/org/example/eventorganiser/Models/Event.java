package org.example.eventorganiser.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="events")
public class Event {
    @Id
    @GeneratedValue
    private int eventId;

    private String eventName;

    private Date eventDate;

    private String eventLocation;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<EventGuests> eventGuests;

    @ManyToMany(mappedBy = "organisedEvents")
    private List<User> organizers;

    public Event() {}

    public Event(String eventName, Date eventDate, String eventLocation) {
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventLocation = eventLocation;
    }
}
