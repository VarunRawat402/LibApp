package com.example.demo.Request;

import com.example.demo.Model.Admin;
import com.example.demo.Model.Student;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UserRequest {


    private String username;

    private String password;

    private String authority;
    private Student student;
    private Admin admin;
}
