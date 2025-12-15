package org.example.eventorganiser.Services;

import org.example.eventorganiser.DTOs.LocationDTO;
import org.example.eventorganiser.Models.*;
import org.example.eventorganiser.Repositories.EventRepository;
import org.example.eventorganiser.Repositories.EventGuestsRepository;
import org.example.eventorganiser.Repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public void addEvent(String eventName, LocalDate eventDate, LocationDTO locationDTO,
                        String description, String schedule, List<Integer> organizersId) throws SQLException {
        Location location = new Location(
                locationDTO.getLatitude(),
                locationDTO.getLongitude(),
                locationDTO.getAddress()
        );
        Event event = new Event();
        event.setEventName(eventName);
        event.setEventDate(eventDate);
        event.setLocation(location);
        event.setDescription(description);
        event.setSchedule(schedule);

        List<User> organizers = new ArrayList<>();
        for (Integer organizerId : organizersId) {
            if (userRepository.findById(organizerId).isEmpty()) {
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

    public List<Event> getAllEventsForUser(Integer userId) throws SQLException {
        if (userRepository.findById(userId).isEmpty()) {
            throw new SQLException("User not found");
        }
        return eventRepository.findAllEventsByUserId(userId);
    }

    public List<Event> getAllEventsCreatedByUser(Integer userId) throws SQLException {
        if (userRepository.findById(userId).isEmpty()) {
            throw new SQLException("User not found");
        }
        return eventRepository.findEventsByOrganizer(userId);
    }

    public Event getEventById(int id) throws SQLException {
        return eventRepository.findById(id)
                .orElseThrow(() -> new SQLException("Event not found"));
    }

    public List<Event> getAllEvents() throws SQLException {
        return eventRepository.findAll();
    }

    public void updateEvent(int id, String eventName, LocalDate eventDate, LocationDTO locationDTO,
                           String description, String schedule) throws SQLException {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new SQLException("Event not found"));

        Location location = new Location(
                locationDTO.getLatitude(),
                locationDTO.getLongitude(),
                locationDTO.getAddress()
        );
        event.setEventName(eventName);
        event.setEventDate(eventDate);
        event.setLocation(location);
        event.setDescription(description);
        event.setSchedule(schedule);

        eventRepository.save(event);
    }

    public void deleteEvent(int id) throws SQLException {
        if(!eventRepository.existsById(id)){
            throw new SQLException("Event not found");
        }
        eventRepository.deleteById(id);
    }

    public void respondToInvitation(int eventId, int userId, InvitationStatus status) {
        EventGuestsId id = new EventGuestsId(userId, eventId);

        EventGuests invitation = eventGuestsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invitation not found"));

        invitation.setStatus(status);
        eventGuestsRepository.save(invitation);
    }

    public List<Event> getPendingInvitations(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        List<EventGuests> pendingGuests = eventGuestsRepository.findByUserAndStatus(user, InvitationStatus.PENDING);
        return pendingGuests.stream().map(EventGuests::getEvent).collect(Collectors.toList());
    }

    public List<Event> getOrganizerEvents(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return eventRepository.findEventsByOrganizer(user.getUserId());
    }

    public List<Event> getAcceptedEvents(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        List<EventGuests> acceptedGuests = eventGuestsRepository.findByUserAndStatus(user, InvitationStatus.ACCEPTED);
        return acceptedGuests.stream().map(EventGuests::getEvent).collect(Collectors.toList());
    }

    public List<Event> getDeclinedEvents(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        List<EventGuests> declinedGuests = eventGuestsRepository.findByUserAndStatus(user, InvitationStatus.DECLINED);
        return declinedGuests.stream().map(EventGuests::getEvent).collect(Collectors.toList());
    }

    public EventGuests createEventGuests(String email, String eventName) {
        System.out.println("User email: " + email);
        User user = userRepository.findByEmail(email);
        Event event = eventRepository.findByEventName(eventName);

        System.out.println("User Id: " + user.getUserId());
        System.out.println("Event Id: " + event.getEventId());

        EventGuests eventGuests = new EventGuests(user, event);

        return eventGuestsRepository.save(eventGuests);
    }

    public List<EventGuests> getEventGuests(int eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        return eventGuestsRepository.findByEvent(event);
    }

    public void addGuestToEvent(int eventId, String email) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        EventGuestsId id = new EventGuestsId(user.getUserId(), eventId);
        if (eventGuestsRepository.existsById(id)) {
            throw new RuntimeException("User is already a guest");
        }

        EventGuests eventGuests = new EventGuests(user, event);
        eventGuestsRepository.save(eventGuests);
    }

    public void removeGuestFromEvent(int eventId, int userId) {
        EventGuestsId id = new EventGuestsId(userId, eventId);
        if (!eventGuestsRepository.existsById(id)) {
            throw new RuntimeException("Guest not found in this event");
        }
        eventGuestsRepository.deleteById(id);
    }

    public void updateGuestStatus(int eventId, int userId, InvitationStatus status) {
        EventGuestsId id = new EventGuestsId(userId, eventId);
        EventGuests guest = eventGuestsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Guest not found"));
        guest.setStatus(status);
        eventGuestsRepository.save(guest);
    }
    public Event updateLocation(Long id, Location location) {
        Event event = eventRepository.findById(id.intValue())
                .orElseThrow(() -> new RuntimeException("Event not found"));
        event.setLocation(location);
        return eventRepository.save(event);
    }


}
