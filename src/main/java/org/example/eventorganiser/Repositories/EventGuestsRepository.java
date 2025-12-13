package org.example.eventorganiser.Repositories;

import org.example.eventorganiser.Models.Event;
import org.example.eventorganiser.Models.EventGuests;
import org.example.eventorganiser.Models.EventGuestsId;
import org.example.eventorganiser.Models.InvitationStatus;
import org.example.eventorganiser.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventGuestsRepository extends JpaRepository<EventGuests, EventGuestsId> {
    List<EventGuests> findByUser(User guest);
    List<EventGuests> findByUserAndStatus(User guest, InvitationStatus status);
    List<EventGuests> findByEvent(Event event);
}
