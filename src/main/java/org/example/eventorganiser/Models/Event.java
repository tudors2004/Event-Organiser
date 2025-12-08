package org.example.eventorganiser.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
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

    private LocalDate eventDate;

    private String eventLocation;

    @Column(length = 2000)
    private String description;

    @Column(length = 5000)
    private String schedule;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<EventGuests> eventGuests;

    @ManyToMany(mappedBy = "organisedEvents")
    private List<User> organizers;

    public Event() {}

    public Event(String eventName, LocalDate eventDate, String eventLocation) {
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventLocation = eventLocation;
    }
}
