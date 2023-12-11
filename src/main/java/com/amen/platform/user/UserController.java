package com.amen.platform.user;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;
    private final EmailService emailService;
    //    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request,
            Principal connectedUser
    ) {
        service.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/sendHtmlEmail")
    public ResponseEntity<String> sendHtmlEmail() {
        try {
            emailService.sendHtmlEmail("amen.jouini18@gmail.com", "Forgot your password?");
            return ResponseEntity.ok("Email sent successfully");
        } catch (MessagingException e) {
            // Log the exception or handle it according to your application's requirements
            return ResponseEntity.status(500).body("Failed to send email");
        }
    }

}
