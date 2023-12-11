package com.amen.platform.user;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendHtmlEmail(String to, String subject) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        message.setFrom(new InternetAddress("${spring.mail.username}"));
        message.setRecipients(MimeMessage.RecipientType.TO,to);
        message.setSubject(subject);

        String body =
                "<p>Reset your password\n" +
                        "You are receiving this email because we received a password reset request for your account.\n" +
                        "If you did not request a password reset, no further action is required.</p>";
        message.setContent(body, "text/html; charset=utf-8");

        mailSender.send(message);
    }
}
