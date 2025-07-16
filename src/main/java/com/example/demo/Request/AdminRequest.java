package com.example.demo.Request;


import com.example.demo.Model.Admin;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.modelmapper.ModelMapper;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class
AdminRequest {

    private String name;
    private int age;

    @NotBlank
    private String username;
    @NotBlank
    private String password;

    public Admin to(ModelMapper modelMapper){
        return modelMapper.map(this,Admin.class);
    }

    public UserRequest toUser(ModelMapper modelMapper){
        UserRequest userRequest =  modelMapper.map(this,UserRequest.class);
        userRequest.setAdmin(to(modelMapper));
        return userRequest;
    }
}
