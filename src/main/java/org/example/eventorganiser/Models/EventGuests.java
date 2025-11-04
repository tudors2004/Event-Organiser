package org.example.eventorganiser.Models;

import jakarta.persistence.*;

@Entity
public class EventGuests {

    @EmbeddedId
    private EventGuestsId id = new EventGuestsId();

    @ManyToOne
    @MapsId("guestId")
    @JoinColumn(name="guest_id")
    private User user;

    @ManyToOne
    @MapsId("eventId")
    @JoinColumn(name="event_id")
    private Event event;

    private Boolean status = false;

    public EventGuests() {}

    public EventGuests(User user, Event event, Boolean status) {
        this.user = user;
        this.event = event;
        this.status = status;
    }

    public EventGuestsId getId() {
        return id;
    }

    public void setId(EventGuestsId id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
