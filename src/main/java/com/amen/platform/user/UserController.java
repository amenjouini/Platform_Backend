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
        return service.getProfil(connectedUser);
    }

    @PutMapping("/update-profil")
    public ResponseEntity<String> updateProfil(Principal connectedUser, @RequestBody User updatedUser) {
        return service.updateProfil(connectedUser,updatedUser);
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
