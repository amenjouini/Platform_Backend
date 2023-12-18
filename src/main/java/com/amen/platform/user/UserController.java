package com.amen.platform.user;

import com.amen.platform.demo.AdminService;
import jakarta.mail.MessagingException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;
    private final EmailService emailService;
    private final UserRepository repository;
    //    @PreAuthorize("hasRole('ADMIN')")

    @GetMapping("/get-profil")
    @ResponseBody
    public User getProfil(
        Principal connectedUser
    ) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        Optional<User> userOptional = repository.findById(user.getId());
        return userOptional.orElse(null);
    }

    @PutMapping("/update-profile")
    public ResponseEntity<String> updateProfile(Principal connectedUser, @RequestBody User updatedUser) {
        // Find the user by ID
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        Optional<User> optionalUser = repository.findById(user.getId());

        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();

            // Update the user properties
            existingUser.setFirstName(updatedUser.getFirstName());
            existingUser.setLastName(updatedUser.getLastName());
            existingUser.setEmail(updatedUser.getEmail());

            // Save the updated user
            repository.save(existingUser);

            return ResponseEntity.ok("User profile updated successfully");
        } else {
            return ResponseEntity.status(404).body("User not found");
        }
    }
    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request,
            Principal connectedUser
    ) {
        service.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/hello")
    public String getHello(){
        return "hey from user controller";
    }



}
