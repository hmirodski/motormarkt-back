package com.backend.motormarkt.emails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })

public class EmailController {
    @Autowired
    private EmailService emailService;

    @PostMapping("/api/send-email")
    public ResponseEntity<Object> sendEmail(@RequestBody EmailRequest emailRequest) {
        // Supongamos que EmailRequest tiene campos como to, subject, text, etc.
        return emailService.sendEmail(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getText());
    }
}
