package com.amen.platform.demo;

import com.amen.platform.auth.RegisterRequest;
import com.amen.platform.user.Role;
import com.amen.platform.user.User;
import com.amen.platform.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManagementService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final AdminService adminService;
    public String addManager(RegisterRequest request){
        var user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(adminService.generateRandomPassword()))
                .role(Role.MANAGER)
                .build();
        repository.save(user);
        return "Manager added successfully";
    }
}
