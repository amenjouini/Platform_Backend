package com.amen.platform.demo;

import com.amen.platform.auth.RegisterRequest;
import com.amen.platform.user.User;
import com.amen.platform.user.UserRepository;
import com.amen.platform.user.UserService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
//@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminService adminService;
    private final ManagementService managementService;
    private final UserService service;
    private final UserRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @GetMapping("/admin")
//    @PreAuthorize("hasAuthority('admin:read')")
    public String get() {


            return "GET:: admin controller";


    }
    private void simulateMessagingException() throws MessagingException {
        throw new MessagingException("Simulated MessagingException");
    }

    @GetMapping("/get-profil")
    @ResponseBody
    public User getProfil(
            Principal connectedUser
    ) {
        return service.getProfil(connectedUser);
    }

//    @GetMapping("/get-all-users-with-admin")
//    @ResponseBody //naheha ken saret prob
//    public ResponseEntity<List<User>> getAllUsers() {
//        List<User> userList = repository.findAll();
//        return ResponseEntity.ok(userList);
//    }

    @GetMapping("/get-all-users")
    @ResponseBody //naheha ken saret prob
    public ResponseEntity<List<User>> getAllUsers(Principal connectedUser) {
        var user = getProfil(connectedUser);
        List<User> userList = repository.findAllByIdNot(user.getId());
        return ResponseEntity.ok(userList);
    }

//    public List<User> getAllUsersExceptConnectedUser(Principal connectedUser) {
//        // Extract the username (email) of the connected user
//        String connectedUsername = connectedUser.getName();
//
//        // Use Spring Data JPA to find all users except the connected user
//        List<User> userList = userRepository.findAllByEmailNot(connectedUsername);
//
//        return userList;
//    }



    @GetMapping("/get-user-byNickname")
    @ResponseBody
    public User getUserByNickname(@RequestParam String nickname) {
        Optional<User> userOptional = repository.findByNickname(nickname);
        System.out.println("User nickname: " + nickname);
        return userOptional.orElse(null);
    }

    @PostMapping
//    @PreAuthorize("hasAuthority('admin:create')")
    public String post(){
        return "POST:: admin controller";
    }

    @PostMapping("/add-admin")
    public String addAdminOrManager(
            @RequestBody RegisterRequest request)  {

        return adminService.addAdminOrManager(request);
    }

    @PostMapping("/add-manager")
    public String addManager(
            @RequestBody RegisterRequest request
    ) throws MessagingException {
        return managementService.addManager(request);
    }

    @PutMapping
//    @PreAuthorize("hasAuthority('admin:update')")
    public String put(){
        return "PUT:: admin controller";
    }

    @PutMapping("/update-profil")
    public ResponseEntity<String> updateProfil(Principal connectedUser, @RequestBody User updatedUser) {
        return service.updateProfil(connectedUser,updatedUser);
    }

    @PutMapping("/block-user")
    public String blockUser(@RequestParam String id) {
        return managementService.blockUser(id);
    }
    @DeleteMapping
//    @PreAuthorize("hasAuthority('admin:delete')")
    public String delete(){
        return "DELETE:: admin controller";
    }

    @DeleteMapping("/delete-user")
    public String deleteUser(@RequestParam String id){
        return managementService.deleteUser(id);
    }
}
