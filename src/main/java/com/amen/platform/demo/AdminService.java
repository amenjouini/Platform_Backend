package com.amen.platform.demo;

import com.amen.platform.auth.RegisterRequest;
import com.amen.platform.user.User;
import com.amen.platform.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public String generateRandomPassword(){
        // Define the character set for the password
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        // Set the length of the password
        int length = 20;

        StringBuilder randomPassword = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();

        for (int i = 0; i < length; i++) {
            // Generate a random index to pick a character from the set
            int randomIndex = secureRandom.nextInt(characters.length());

            // Append the randomly picked character to the password
            randomPassword.append(characters.charAt(randomIndex));
        }

        return randomPassword.toString();
    }
    public String addAdminOrManager(RegisterRequest request){
        var user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(generateRandomPassword()))
                .role(request.getRole())
                .build();
        repository.save(user);
        return request.getRole() + " added successfully";
    }
}
