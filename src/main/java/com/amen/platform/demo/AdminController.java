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
    public String get() throws MessagingException{

        try {
            // Your logic here
            simulateMessagingException();
            return "GET:: admin controller";
        } catch (MessagingException e) {
            // Log the exception using SLF4J
            logger.error("An error occurred while processing the request", e);

            // You can return a custom error message or handle it in a way that suits your application
            return "An error occurred while processing the request";
        }
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

    @DeleteMapping
//    @PreAuthorize("hasAuthority('admin:delete')")
    public String delete(){
        return "DELETE:: admin controller";
    }

    @DeleteMapping("/delete-user")
    public String deleteUser(@RequestBody RegisterRequest request){
        return managementService.deleteUser(request.getEmail());
    }
}
