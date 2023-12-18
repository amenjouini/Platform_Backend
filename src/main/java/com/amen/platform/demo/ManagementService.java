package com.amen.platform.demo;

import com.amen.platform.auth.RegisterRequest;
import com.amen.platform.token.JwtTokenUtil;
import com.amen.platform.token.TokenRepository;
import com.amen.platform.user.Role;
import com.amen.platform.user.User;
import com.amen.platform.user.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ManagementService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final AdminService adminService;

    private final TokenRepository tokenRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    public String addManager(RegisterRequest request) throws MessagingException {
        var generatedPWD = adminService.generateRandomPassword();
        var user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(generatedPWD))
                .role(Role.MANAGER)
                .build();
        repository.save(user);
        MimeMessage message = mailSender.createMimeMessage();
        message.setFrom(new InternetAddress("${spring.mail.username}"));
        message.setRecipients(MimeMessage.RecipientType.TO, request.getEmail());
        message.setSubject(request.getRole()+" Login");
        message.setText("<div> Login using your email and this password: " +request.getEmail()+generatedPWD+ "<a href=\"http://localhost:8080/login" + "\">Login</a></div>");

        mailSender.send(message);
        return request.getRole() + " added successfully";
    }

    public String deleteUser(String email) {
        var user = repository.findByEmail(email);
        System.out.println("email is : "+email);
        if (user.isPresent()) {
            // Step 2: Find and delete associated tokens by user ID
            tokenRepository.deleteByUserId(user.get().getId());

            // Step 3: Delete the user
            repository.delete(user.get());
            return "User deleted successfully";
            //repository.deleteById(user.get(Id));
        } else {
            return "User not found";
        }
    }


//    public String getAllUsers() {
//        return repository.findAll().toString();
//    }

//    public String getUserById(String id) {
//        Optional<User> userOptional = repository.findById(id);
//
//        return userOptional
//                .map(User::toString)
//                .orElse("User not found");
//    }


}
