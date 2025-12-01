package org.example.eventorganiser.Controllers;

import jakarta.mail.MessagingException;
import org.example.eventorganiser.DTOs.EmailRequest;
import org.example.eventorganiser.Services.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/mail")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> send(@RequestBody EmailRequest request) {
        try {
            emailService.sendEmail(request.getRecipients(), request.getName(), request.getEventName());
            return ResponseEntity.ok("Emails sent successfully!");
        }
        catch (MessagingException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
