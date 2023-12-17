package com.amen.platform.demo;

import com.amen.platform.auth.RegisterRequest;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
//@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminService adminService;
    private final ManagementService managementService;
    @GetMapping
//    @PreAuthorize("hasAuthority('admin:read')")
    public String get(){
        return "GET:: admin controller";
    }

    @PostMapping
//    @PreAuthorize("hasAuthority('admin:create')")
    public String post(){
        return "POST:: admin controller";
    }

    @PostMapping("/add-admin")
    public String addAdminOrManager(
            @RequestBody RegisterRequest request) throws MessagingException {

        return adminService.addAdminOrManager(request);
    }

    @PutMapping
//    @PreAuthorize("hasAuthority('admin:update')")
    public String put(){
        return "PUT:: admin controller";
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
