package org.example.eventorganiser.Services;

import org.example.eventorganiser.Models.Event;
import org.example.eventorganiser.Models.User;
import org.example.eventorganiser.Repositories.EventRepository;
import org.example.eventorganiser.Repositories.UserRepository;
import org.springframework.stereotype.Service;


import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public EventService(EventRepository eventRepository, UserRepository userRepository) {
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
}
