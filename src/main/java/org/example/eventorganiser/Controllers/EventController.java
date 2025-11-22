package org.example.eventorganiser.Controllers;

import org.example.eventorganiser.DTOs.CreateEventRequest;
import org.example.eventorganiser.Models.Event;
import org.example.eventorganiser.Services.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

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
            eventService.addEvent(request.getEventName(), request.getEventDate(), request.getEventLocation(), request.getOrganizersId());
            return ResponseEntity.ok("Event created successfully");
        } catch (SQLException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getEventById(@PathVariable Long id){
        try{
            Event event = eventService.getEventById(id);
            return ResponseEntity.ok(event);
        } catch (SQLException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping
    public ResponseEntity<?> getAllEvents(){
        try{
            List<Event> events = eventService.getAllEvents();
            return ResponseEntity.ok(events);
        } catch (SQLException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEvent(@PathVariable Long id, @RequestBody CreateEventRequest request){
        try{
            eventService.updateEvent(id, request.getEventName(), request.getEventDate(), request.getEventLocation());
            return ResponseEntity.ok("Event updated successfully");
        } catch (SQLException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable Long id){
        try{
            eventService.deleteEvent(id);
            return ResponseEntity.ok("Event deleted successfully");
        } catch (SQLException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
