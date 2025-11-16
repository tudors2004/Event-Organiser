package org.example.eventorganiser.Models;

import jakarta.persistence.*;

@Entity
public class EventGuests {

    @EmbeddedId
    private EventGuestsId id = new EventGuestsId();

    @ManyToOne
    @MapsId("guestId")
    @JoinColumn(name="guest_user_id")
    private User user;

    @ManyToOne
    @MapsId("eventId")
    @JoinColumn(name="event_id")
    private Event event;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InvitationStatus status = InvitationStatus.PENDING;

    public EventGuests() {}

    public EventGuests(User user, Event event, InvitationStatus status){
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

    public InvitationStatus getStatus() {
        return status;
    }

    public void setStatus(InvitationStatus status) {
        this.status = status;
    }
}
