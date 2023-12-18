package com.amen.platform.demo;

import com.amen.platform.auth.AuthenticateRequest;
import com.amen.platform.auth.RegisterRequest;
import com.amen.platform.user.User;
import com.amen.platform.user.UserRepository;
import com.amen.platform.user.UserService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/management")
@RequiredArgsConstructor
public class ManagementController {

    private final ManagementService managementService;
    private final UserRepository repository;
    private final UserService service;
    @GetMapping
    public String get(){
        return "GET:: management controller";
    }

    @GetMapping("/get-profil")
    @ResponseBody
    public User getProfil(
            Principal connectedUser
    ) {
        return service.getProfil(connectedUser);
    }

    @GetMapping("/get-all-users")
    @ResponseBody //naheha ken saret prob
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> userList = repository.findAll();
        return ResponseEntity.ok(userList);
    }

    @GetMapping("/get-user-byId")
//    @ResponseBody
    public User getUserById(@RequestParam String id){
        Optional<User> userOptional = repository.findById(id);

        return userOptional.orElse(null);
    }

    @PostMapping
    public String post(){
        return "POST:: management controller";
    }

    @PostMapping("/add-manager")
    public String addManager(
            @RequestBody RegisterRequest request
    ) throws MessagingException {
        return managementService.addManager(request);
    }

    @PutMapping
    public String put(){
        return "PUT:: management controller";
    }

    @PutMapping("/update-profil")
    public ResponseEntity<String> updateProfil(Principal connectedUser, @RequestBody User updatedUser) {
        return service.updateProfil(connectedUser,updatedUser);
    }

    @DeleteMapping
    public String delete(){
        return "DELETE:: management controller";
    }

    @DeleteMapping("/delete-user")
    public String deleteUser(@RequestBody RegisterRequest request){
        return managementService.deleteUser(request.getEmail());
    }
}
