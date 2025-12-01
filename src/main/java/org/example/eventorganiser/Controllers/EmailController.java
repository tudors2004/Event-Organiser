package org.example.eventorganiser.Controllers;

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
        emailService.sendEmail(request.getRecipients(), request.getSubject(), request.getMessage());
        return ResponseEntity.ok("Emails sent successfully!");
    }
}
