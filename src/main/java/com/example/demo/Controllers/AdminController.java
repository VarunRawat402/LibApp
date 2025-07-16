package com.example.demo.Controllers;

import com.example.demo.Model.User;
import com.example.demo.Request.AdminRequest;
import com.example.demo.Service.AdminService;
import com.example.demo.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name="Admin APIs",description = "Create / Delete Admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @Autowired
    UserService userService;

    //Create an Admin
    @PostMapping("/admin")
    @Operation(summary = "Create the Admin")
    public String create(@RequestBody AdminRequest adminRequest){
        adminService.create(adminRequest);
        return "Admin has been created successfully.";
    }

    //Delete an Admin
    @DeleteMapping("/admin")
    @Operation(summary = "Delete the Admin")
    public String delete(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        int adminId = user.getAdmin().getId();
        adminService.delete(adminId);
        userService.delete(user.getId());
        return "Your account has been deleted successfully";
    }
}
