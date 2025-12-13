package org.example.eventorganiser.Controllers;

import org.example.eventorganiser.DTOs.CreateEventRequest;
import org.example.eventorganiser.Mapper.EventMapper;
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
import org.example.eventorganiser.Models.EventGuests;
import org.example.eventorganiser.Mapper.EventGuestsMapper;

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
            eventService.addEvent(
                request.getEventName(),
                request.getEventDate(),
                request.getEventLocation(),
                request.getDescription(),
                request.getSchedule(),
                request.getOrganizersId()
            );
            return ResponseEntity.ok("Event created successfully");
        } catch (SQLException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getAllEventsForUser(@PathVariable Integer userId){
        try{
            List<Event> events = eventService.getAllEventsForUser(userId);
            return ResponseEntity.ok(events.stream().map(EventMapper::toDto).toList());
        } catch (SQLException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/created/user/{userId}")
    public ResponseEntity<?> getAllEventsCreatedByUser(@PathVariable Integer userId){
        try{
            List<Event> events = eventService.getAllEventsCreatedByUser(userId);
            return ResponseEntity.ok(events.stream().map(EventMapper::toDto).toList());
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
            return ResponseEntity.ok(events.stream().map(EventMapper::toDto).toList());
        } catch (SQLException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEvent(@PathVariable int id, @RequestBody CreateEventRequest request){
        try{
            eventService.updateEvent(
                id,
                request.getEventName(),
                request.getEventDate(),
                request.getEventLocation(),
                request.getDescription(),
                request.getSchedule()
            );
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
            @RequestParam int userId) {
        try {
            eventService.respondToInvitation(eventId, userId, status);
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
    public ResponseEntity<?> acceptInvitation(@PathVariable int eventId,@RequestParam int userId) {
        try {
            eventService.respondToInvitation(eventId, userId, InvitationStatus.ACCEPTED);
            return ResponseEntity.ok("Invitation accepted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{eventId}/invitation/decline")
    public ResponseEntity<?> declineInvitation(@PathVariable int eventId, @RequestParam int userId) {
        try {
            eventService.respondToInvitation(eventId, userId, InvitationStatus.DECLINED);
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

    @GetMapping("/{eventId}/guests")
    public ResponseEntity<?> getEventGuests(@PathVariable int eventId) {
        try {
            List<EventGuests> guests = eventService.getEventGuests(eventId);
            return ResponseEntity.ok(guests.stream().map(EventGuestsMapper::toDto).toList());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{eventId}/guests")
    public ResponseEntity<?> addGuestToEvent(@PathVariable int eventId, @RequestParam String email) {
        try {
            eventService.addGuestToEvent(eventId, email);
            return ResponseEntity.ok("Guest added successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{eventId}/guests/{userId}")
    public ResponseEntity<?> removeGuestFromEvent(@PathVariable int eventId, @PathVariable int userId) {
        try {
            eventService.removeGuestFromEvent(eventId, userId);
            return ResponseEntity.ok("Guest removed successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{eventId}/guests/{userId}")
    public ResponseEntity<?> updateGuestStatus(@PathVariable int eventId, @PathVariable int userId, @RequestParam InvitationStatus status) {
        try {
            eventService.updateGuestStatus(eventId, userId, status);
            return ResponseEntity.ok("Guest status updated successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}