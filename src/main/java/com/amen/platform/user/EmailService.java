package com.amen.platform.user;

import com.amen.platform.token.JwtTokenUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public void sendHtmlEmail(String to) throws MessagingException {
        String token = jwtTokenUtil.generateForgetToken(to);

        MimeMessage message = mailSender.createMimeMessage();
        message.setFrom(new InternetAddress("${spring.mail.username}"));
        message.setRecipients(MimeMessage.RecipientType.TO, to);
        message.setSubject("Set your password");
        message.setText("<div> <a href=\"http://localhost:8080/setPassword?token=" + token + "\">Set Password</a></div>");

        mailSender.send(message);
    }
}
