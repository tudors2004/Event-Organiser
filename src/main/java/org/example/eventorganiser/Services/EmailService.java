package org.example.eventorganiser.Services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.List;

@Service
public class EmailService {

    private JavaMailSender mailSender;

    private final TemplateEngine templateEngine;

    public EmailService(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    public void sendEmail(List<String> to, String name, String eventName) throws MessagingException {
            Context context = new Context();
            context.setVariable("name", name);
            context.setVariable("eventName", eventName);

            String emailContent = templateEngine.process("mail", context);
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setTo(to.toArray(new String[0]));
            helper.setSubject("Invitation!!!!");
            helper.setText(emailContent, true);

        mailSender.send(mimeMessage);
    }
}
