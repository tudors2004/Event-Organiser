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

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String event_location) {
        this.eventLocation = event_location;
    }

    public List<EventGuests> getEventGuests() {
        return eventGuests;
    }

    public void setEventGuests(List<EventGuests> eventGuests) {
        this.eventGuests = eventGuests;
    }

    public List<User> getOrganizers() {
        return organizers;
    }

    public void setOrganizers(List<User> organizers) {
        this.organizers = organizers;
    }
}
