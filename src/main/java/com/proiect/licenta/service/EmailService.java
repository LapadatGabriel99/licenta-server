package com.proiect.licenta.service;

import com.proiect.licenta.mail.EmailSender;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@AllArgsConstructor
public class EmailService implements EmailSender {

    @Autowired
    private final JavaMailSender mailSender;

    @Async
    @Override
    public void send(String to, String message) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        helper.setFrom("onlyforlicenta@yahoo.com");
        helper.setTo(to);
        helper.setSubject("Confirm your email");
        helper.setText(message, true);

        mailSender.send(mimeMessage);
    }
}
