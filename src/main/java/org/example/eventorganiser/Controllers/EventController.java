package org.example.eventorganiser.Controllers;

import org.example.eventorganiser.DTOs.CreateEventRequest;
import org.example.eventorganiser.Models.Event;
import jakarta.validation.Valid;
import org.example.eventorganiser.Models.InvitationStatus;
import org.example.eventorganiser.Models.User;
import org.example.eventorganiser.Services.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

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
    public ResponseEntity<?> createEvent(@Valid @RequestBody CreateEventRequest request){
        try{
            eventService.addEvent(request.getEventName(), request.getEventDate(), request.getEventLocation(), request.getOrganizersId());
            return ResponseEntity.ok("Event created successfully");
        } catch (SQLException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getAllEventsForUser(@PathVariable Integer userId){
        try{
            List<Event> events = eventService.getAllEventsForUser(userId);
            return ResponseEntity.ok(events);
        } catch (SQLException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEventById(@PathVariable int id){
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
    public ResponseEntity<?> updateEvent(@PathVariable int id, @RequestBody CreateEventRequest request){
        try{
            eventService.updateEvent(id, request.getEventName(), request.getEventDate(), request.getEventLocation());
            return ResponseEntity.ok("Event updated successfully");
        } catch (SQLException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable int id){
        try{
            eventService.deleteEvent(id);
            return ResponseEntity.ok("Event deleted successfully");
        } catch (SQLException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{eventId}/respond")
    public ResponseEntity<?> respondToInvitation(
            @PathVariable int eventId,
            @RequestParam InvitationStatus status,
            @AuthenticationPrincipal User user) {
        try {
            eventService.respondToInvitation(eventId, user, status);
            return ResponseEntity.ok("Invitation response recorded successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/guest/pending")
    public ResponseEntity<?> getPendingInvitations(@AuthenticationPrincipal User user) {
        try {
            List<Event> events = eventService.getPendingInvitations(user.getEmail());
            return ResponseEntity.ok(events);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{eventId}/invitation/accept")
    public ResponseEntity<?> acceptInvitation(@PathVariable int eventId, @AuthenticationPrincipal User user) {
        try {
            eventService.respondToInvitation(eventId, user, InvitationStatus.ACCEPTED);
            return ResponseEntity.ok("Invitation accepted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{eventId}/invitation/decline")
    public ResponseEntity<?> declineInvitation(@PathVariable int eventId, @AuthenticationPrincipal User user) {
        try {
            eventService.respondToInvitation(eventId, user, InvitationStatus.DECLINED);
            return ResponseEntity.ok("Invitation declined successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/organizer")
    public ResponseEntity<?> getOrganizerEvents(@AuthenticationPrincipal User user) {
        try {
            List<Event> events = eventService.getOrganizerEvents(user.getEmail());
            return ResponseEntity.ok(events);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/guest/accepted")
    public ResponseEntity<?> getAcceptedEvents(@AuthenticationPrincipal User user) {
        try {
            List<Event> events = eventService.getAcceptedEvents(user.getEmail());
            return ResponseEntity.ok(events);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/guest/declined")
    public ResponseEntity<?> getDeclinedEvents(@AuthenticationPrincipal User user) {
        try {
            List<Event> events = eventService.getDeclinedEvents(user.getEmail());
            return ResponseEntity.ok(events);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}