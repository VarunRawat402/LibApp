package com.example.demo.Service;

import com.example.demo.Model.Admin;
import com.example.demo.Model.User;
import com.example.demo.Repository.AdminRepository;
import com.example.demo.Request.AdminRequest;
import com.example.demo.Request.UserRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

@Service
public class AdminService {

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    UserService userService;

    @Autowired
    ModelMapper modelMapper;

    public void create(AdminRequest adminRequest){

        //User created and saved
        UserRequest userRequest = adminRequest.toUser(modelMapper);
        User user = userService.create(userRequest);

        //User set to admin and admin saved
        Admin admin = adminRequest.to(modelMapper);
        admin.setUser(user);
        adminRepository.save(admin);
    }

    public void delete(int id){
        adminRepository.deleteById(id);
    }
}
