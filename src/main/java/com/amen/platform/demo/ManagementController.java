package com.amen.platform.demo;

import com.amen.platform.auth.AuthenticateRequest;
import com.amen.platform.auth.RegisterRequest;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/management")
@RequiredArgsConstructor
public class ManagementController {

    private final ManagementService managementService;
    @GetMapping
    public String get(){
        return "GET:: management controller";
    }

    @GetMapping("/get-all-users")
    public String getAllUsers(){
        return managementService.getAllUsers();
    }

    @GetMapping("/get-user-byId")
    public String getUserById(@RequestParam String id){
        return managementService.getUserById(id);
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

    @DeleteMapping
    public String delete(){
        return "DELETE:: management controller";
    }

    @DeleteMapping("/delete-user")
    public String deleteUser(@RequestBody RegisterRequest request){
        return managementService.deleteUser(request.getEmail());
    }
}
