package com.example.demo.Request;

import com.example.demo.Enums.AccountStatus;
import com.example.demo.Model.Student;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.modelmapper.ModelMapper;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class StudentRequest {

    @NotBlank
    private String name;
    private int age;
    private String contact;
    private String email;
    private AccountStatus accountStatus;

    @NotBlank
    private String username;
    @NotBlank
    private String password;

    public Student to(ModelMapper modelMapper) {
        return modelMapper.map(this, Student.class);
    }

    public UserRequest toUser(ModelMapper modelMapper) {
        UserRequest userRequest = modelMapper.map(this, UserRequest.class);
        userRequest.setStudent(to(modelMapper)); // Map Student separately
        return userRequest;
    }
}
