package com.example.demo.Service;

import com.example.demo.Enums.Authority;
import com.example.demo.Model.User;
import com.example.demo.Repository.UserCacheRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Request.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService{


    @Autowired
    UserCacheRepository userCacheRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    //Authenticating the user from the redis cache if not found in redis then from DB
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

//        User user = userCacheRepository.get(username);
//        if(user==null){
//            user = userRepository.findByUsername(username);
//            if(user!=null){
//                userCacheRepository.set(user);
//            }
//        }
        User user = userRepository.findByUsername(username);
        return user;
    }

    public User create(UserRequest userRequest){

        User user = User.builder()
                .username(userRequest.getUsername())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .build();

        if(userRequest.getStudent() != null){
            user.setAuthority(Authority.STUDENT_AUTHORITY);
        }else{
            user.setAuthority(Authority.ADMIN_AUTHORITY);
        }

        return userRepository.save(user);
    }

    public void delete(int userId){
        userRepository.deleteById(userId);
    }
}
