package com.amen.platform.user;

import com.amen.platform.config.JwtService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private JwtService jwtService;

    public void sendHtmlEmail(String to, String subject, String resetToken) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();


        // Include the token in the password reset link
        String resetLink = "http://your-app-domain/reset-password?token=" + resetToken;

        message.setFrom(new InternetAddress("${spring.mail.username}"));
        message.setRecipients(MimeMessage.RecipientType.TO,to);
        message.setSubject(subject);

        String body =
                "<p>Reset your password\n" +
                        "You are receiving this email because we received a password reset request for your account.\n" +
                        "If you did not request a password reset, no further action is required.</p>" +
                        "<p><a href=\"" + resetLink + "\">Reset Password</a></p>";

        message.setContent(body, "text/html; charset=utf-8");

        mailSender.send(message);
    }
}
