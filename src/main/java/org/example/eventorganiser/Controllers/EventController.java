package org.example.eventorganiser.Controllers;

import org.example.eventorganiser.DTOs.CreateEventRequest;
import org.example.eventorganiser.Models.Event;
import org.example.eventorganiser.Repositories.EventRepository;
import org.example.eventorganiser.Services.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/createEvent")
    public ResponseEntity<?> createEvent(@RequestBody CreateEventRequest request){
        try{
            eventService.addEvent(request.getEventName(), request.getEventDate(), request.getEventLocation(), request.getOrganizers());
            return ResponseEntity.ok("Event created successfully");
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
