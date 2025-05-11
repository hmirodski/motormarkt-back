package com.backend.motormarkt.emails;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    public ResponseEntity<Object> sendEmail(String to, String subject, String text) {
        HashMap<String, Object> data = new HashMap<>();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        javaMailSender.send(message);

        data.put("message", "Mensaje enviado");
        return new ResponseEntity<>(
                data,
                HttpStatus.CREATED
        );
    }
}
