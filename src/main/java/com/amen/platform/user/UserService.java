package com.amen.platform.user;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;
    private final EmailService emailService;
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


            emailService.sendHtmlEmail(email);

            return "Please check your email to set your new password";
//        } catch (Exception e) {
//            throw new RuntimeException("An error occurred while processing the request");
//        }
    }

    public String setPassword(String email,
                              ChangePasswordRequest request) throws MessagingException{
        //try {
            var user = repository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found with this email: " + email));
            if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
                throw new IllegalStateException("New password does not match the confirmation password");
            }
            // Update password
            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            repository.save(user);
            return "Password set";
//        } catch (Exception e) {
//            throw new IllegalStateException("Error changing password", e);
//        }

    }
}

