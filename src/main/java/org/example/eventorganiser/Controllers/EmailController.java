package org.example.eventorganiser.Controllers;

import jakarta.mail.MessagingException;
import org.example.eventorganiser.DTOs.EmailRequest;
import org.example.eventorganiser.Services.EmailService;
import org.example.eventorganiser.Services.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/mail")
public class EmailController {

    private final EmailService emailService;

    private final EventService eventService;

    public EmailController(EmailService emailService,  EventService eventService) {
        this.emailService = emailService;
        this.eventService = eventService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> send(@RequestBody EmailRequest request) {
        try {
            emailService.sendEmail(request.getRecipients(), request.getName(), request.getEventName());
            request.getRecipients().stream().forEach(email -> eventService.createEventGuests(email, request.getEventName()));
            return ResponseEntity.ok("Emails sent successfully!");
        }
        catch (MessagingException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
