package org.example.eventorganiser.Models;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class EventGuestsId implements Serializable {
    private int guestId;

    private int eventId;

    public EventGuestsId() {}

    public EventGuestsId(int guestId, int eventId) {
        this.guestId = guestId;
        this.eventId = eventId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(guestId, eventId);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(!(obj instanceof EventGuestsId)) return false;
        return Objects.equals(guestId, ((EventGuestsId)obj).guestId) &&
                Objects.equals(eventId, ((EventGuestsId)obj).eventId);
    }

    public int getGuestId() {
        return guestId;
    }

    public void setGuestId(int guestId) {
        this.guestId = guestId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }
}
