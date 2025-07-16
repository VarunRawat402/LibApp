package com.example.demo.Service;

import com.example.demo.Model.Student;
import com.example.demo.Model.User;
import com.example.demo.Repository.StudentRepository;
import com.example.demo.Request.StudentRequest;
import com.example.demo.Request.UserRequest;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

@Service
public class StudentService {

    private  ModelMapper modelMapper;
    private final StudentRepository sr;
    private final UserService userService;

    public StudentService(ModelMapper modelMapper,StudentRepository sr,UserService userService){
        this.modelMapper = modelMapper;
        this.sr=sr;
        this.userService=userService;
    }


    public void create(@Valid StudentRequest studentRequest) {

        UserRequest userRequest = studentRequest.toUser(modelMapper);
        User user = userService.create(userRequest);
        Student student = studentRequest.to(modelMapper);
        student.setUser(user);
        sr.save(student);
    }

    public Student findStudentById(int id){
        return sr.findById(id).orElse(null);
    }

    public void delete(int studentId) {
        sr.deleteById(studentId);
    }

    public Student findByEmail(String email){
        return sr.findByEmail(email);
    }
}
