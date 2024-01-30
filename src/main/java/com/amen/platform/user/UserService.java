package com.amen.platform.user;

import com.amen.platform.token.JwtTokenUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;
    private final EmailService emailService;
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    public void changePassword(
            ChangePasswordRequest request,
            Principal connectedUser
    ) {
        try {
            var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

            // Check if the current password is correct
            if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
                throw new IllegalStateException("Wrong current password");
            }

            // Check if the new password matches the confirmation password
            if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
                throw new IllegalStateException("New password does not match the confirmation password");
            }

            // Update password
            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            repository.save(user);
        } catch (Exception e) {
            throw new IllegalStateException("Error changing password", e);
        }
    }

    public String forgetPassword(String email) throws MessagingException {
        //try {
            System.out.println("Email received: " + email);
            repository.findByEmail(String.valueOf(email))
                    .ifPresent(user -> System.out.println("Found user with email: " + user.getEmail()));

            // If no user is found, throw an exception
            repository.findByEmail(String.valueOf(email))
                    .orElseThrow(() -> new RuntimeException("User not found with this email: " + email));
            //emailService.sendHtmlEmail(email);

            String token = jwtTokenUtil.generateForgetToken(email);

            MimeMessage message = mailSender.createMimeMessage();
            message.setFrom(new InternetAddress("${spring.mail.username}"));
            message.setRecipients(MimeMessage.RecipientType.TO, email);
            message.setSubject("Set your password");
            message.setText("<div> <a href=\"http://localhost:8080/setPassword?token=" + token + "\">Set Password</a></div>");

            mailSender.send(message);

            return "Please check your email to set your new password";
//        } catch (Exception e) {
//            throw new RuntimeException("An error occurred while processing the request");
//        }
    }

    public String setPassword(String email,
                              ChangePasswordRequest request) throws MessagingException{
        try {
            var user = repository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found with this email: " + email));
            if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
                throw new IllegalStateException("New password does not match the confirmation password");
            }
            // Update password
            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            repository.save(user);
            return "Password set";
        } catch (Exception e) {
            throw new IllegalStateException("Error changing password", e);
        }
    }

    public ResponseEntity<String> updateProfil(Principal connectedUser, User updatedUser) {
        // Find the user by ID
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        Optional<User> optionalUser = repository.findById(user.getId());

        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();

            // Update the user properties
            existingUser.setFirstname(updatedUser.getFirstname());
            existingUser.setLastname(updatedUser.getLastname());
            existingUser.setEmail(updatedUser.getEmail()); //need to make it more secure

            // Save the updated user
            repository.save(existingUser);

            return ResponseEntity.ok("User profile updated successfully");
        } else {
            return ResponseEntity.status(404).body("User not found");
        }
    }

    public User getProfil(Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        Optional<User> userOptional = repository.findById(user.getId());
        return userOptional.orElse(null);
    }
}

