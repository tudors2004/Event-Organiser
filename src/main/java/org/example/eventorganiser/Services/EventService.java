package org.example.eventorganiser.Services;

import org.example.eventorganiser.Models.Event;
import org.example.eventorganiser.Models.User;
import org.example.eventorganiser.Repositories.EventRepository;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class EventService {
    private final EventRepository eventRepository;


    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event addEvent(String eventName, Date eventDate, String eventLocation, List<User> organizers){
        Event event = new Event();
        event.setEventName(eventName);
        event.setEventDate(eventDate);
        event.setEventLocation(eventLocation);
        event.setOrganizers(organizers);

        return eventRepository.save(event);
    }
}
