package com.amen.platform.auth;

import com.amen.platform.demo.AdminService;
import com.amen.platform.token.JwtTokenUtil;
import com.amen.platform.user.ChangePasswordRequest;
import com.amen.platform.user.User;
import com.amen.platform.user.UserRepository;
import com.amen.platform.user.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private User user;
    private final UserRepository repository;
    private final AuthenticationService service;
    //public final EmailService emailService;
    public final UserService userService;
    private final AdminService adminService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ){
        return ResponseEntity.ok(service.register(request));
    }

    @GetMapping("/getRandomPWD")
    public String getPWD(){
        return adminService.generateRandomPassword();
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticateRequest request
    ) throws MessagingException
    {
        return ResponseEntity.ok(service.authenticate(request));
    }


    @PostMapping("/forget-password")
    public String forgetPassword(
            @RequestBody AuthenticateRequest request
    ) throws MessagingException {
        String email = request.getEmail();
        return userService.forgetPassword(email);
    }
    @PostMapping("/set-password")
    public String setPassword(@RequestParam String token,
                              @RequestBody ChangePasswordRequest request) throws MessagingException {
        // Validate the token
        String email = jwtTokenUtil.getEmailFromToken(token);
        if (email != null) {
            userService.setPassword(email,request);
            return "Password set successfully";
        } else {
            return "Invalid token";
        }
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request ,
            HttpServletResponse response
    ) throws IOException {
         service.refreshToken(request, response);
    }

}
