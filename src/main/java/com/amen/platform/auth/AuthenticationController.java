package com.amen.platform.auth;

import com.amen.platform.user.User;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private User user;
    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ){
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticateRequest request
    ) throws MessagingException
//            throws MessagingException {
//        if (request.isForgetPassword(true)){
//            return ResponseEntity.ok(service.forgetPassword(request));
//        }
    {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/forget-password")
    public ResponseEntity<?> forgetPassword(
            @RequestBody AuthenticateRequest request
    ) throws MessagingException {
        if (request.isForgetPassword()) {
            // Assuming that the service.forgetPassword method returns something meaningful
            // and you want to return it in the response.
            AuthenticationResponse response = service.forgetPassword(request);
            return ResponseEntity.ok(response);
        } else {
            // Handle the case where forgetPassword flag is not set
            String errorMessage = "Forget password flag is not set. Please include the forgetPassword field in your request.";
            return ResponseEntity.badRequest().body(errorMessage);
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
