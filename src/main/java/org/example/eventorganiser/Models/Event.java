package org.example.eventorganiser.Models;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.List;


@Entity
@Table(name="events")
public class Event {
    @Id
    @GeneratedValue
    private int eventId;

    private String eventName;

    private Date eventDate;

    private String event_location;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<EventGuests> event_guests;

    @ManyToMany(mappedBy = "organisedEvents")
    private List<User> organizers;

    public Event() {}

    public Event(String eventName, Date eventDate, String event_location) {
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.event_location = event_location;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public String getEvent_location() {
        return event_location;
    }

    public void setEvent_location(String event_location) {
        this.event_location = event_location;
    }

    public List<EventGuests> getEvent_guests() {
        return event_guests;
    }

    public void setEvent_guests(List<EventGuests> event_guests) {
        this.event_guests = event_guests;
    }

    public List<User> getOrganizers() {
        return organizers;
    }

    public void setOrganizers(List<User> organizers) {
        this.organizers = organizers;
    }
}
