package com.example.simpleHealth.services;

import com.example.simpleHealth.models.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Service
@RequiredArgsConstructor
public class EmailService {
    public static final String EMAIL = "aanastasia.avdeeva@yandex.ru";
    @Autowired
    private JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void sendNotification(Order order) throws MailException {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(order.getEmail());
        mail.setFrom(EMAIL);
        System.out.println("------------------------");
        System.out.println("New order at " +
                DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
                        .format(LocalDateTime.now()));
        mail.setSubject("New order at " +
                DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
                        .format(LocalDateTime.now()));
        mail.setText(order.toString());

        javaMailSender.send(mail);
    }

}
