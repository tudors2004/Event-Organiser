package org.example.eventorganiser.Services;

import org.example.eventorganiser.Models.*;
import org.example.eventorganiser.Repositories.EventRepository;
import org.example.eventorganiser.Repositories.EventGuestsRepository;
import org.example.eventorganiser.Repositories.UserRepository;
import org.springframework.stereotype.Service;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final EventGuestsRepository eventGuestsRepository;

    public EventService(EventRepository eventRepository, UserRepository userRepository, EventGuestsRepository eventGuestsRepository) {
        this.eventGuestsRepository = eventGuestsRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    public void addEvent(String eventName, LocalDate eventDate, String eventLocation, List<Integer> organizersId) throws SQLException {
        Event event = new Event();
        event.setEventName(eventName);
        event.setEventDate(eventDate);
        event.setEventLocation(eventLocation);
        List<User> organizers = new ArrayList<>();
        for(Integer organizerId : organizersId){
            if(userRepository.findById(organizerId).isEmpty()){
                throw new SQLException("Organizer was not found");
            }
            User organizer = userRepository.findById(organizerId).get();
            organizers.add(organizer);

            organizer.getOrganisedEvents().add(event);
        }

        event.setOrganizers(organizers);

        eventRepository.save(event);
        userRepository.saveAll(organizers);
    }
    public Event getEventById(Long id) throws SQLException {
        return eventRepository.findById(id)
                .orElseThrow(() -> new SQLException("Event not found"));
    }

    public List<Event> getAllEvents() throws SQLException {
        return eventRepository.findAll();
    }

    public void updateEvent(Long id, String eventName, LocalDate eventDate, String eventLocation) throws SQLException {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new SQLException("Event not found"));

        event.setEventName(eventName);
        event.setEventDate(eventDate);
        event.setEventLocation(eventLocation);

        eventRepository.save(event);
    }

    public void deleteEvent(Long id) throws SQLException {
        if(!eventRepository.existsById(id)){
            throw new SQLException("Event not found");
        }
        eventRepository.deleteById(id);
    }
    public List<EventGuests> getInvitationsForUser(User user) {
        return eventGuestsRepository.findByUser(user);
    }
    public List<EventGuests> getPendingInvitationsForUser(User user) {
        return eventGuestsRepository.findByUserAndStatus(user, InvitationStatus.PENDING);
    }
    public List<EventGuests> getAcceptedInvitationsForUser(User user) {
        return eventGuestsRepository.findByUserAndStatus(user, InvitationStatus.ACCEPTED);
    }
    public List<EventGuests> getDeclinedInvitationsForUser(User user) {
        return eventGuestsRepository.findByUserAndStatus(user, InvitationStatus.DECLINED);
    }

    public void respondToInvitation(int eventId, User user, InvitationStatus status) {
        EventGuestsId id = new EventGuestsId(user.getUserId(), eventId);

        EventGuests invitation = eventGuestsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invitation not found"));

        if (!invitation.getUser().getUserId().equals(user.getUserId())) {
            throw new RuntimeException("Not authorized");
        }

        invitation.setStatus(status);
        eventGuestsRepository.save(invitation);
    }

}
