package com.example.demo;

import com.example.demo.Model.Admin;
import com.example.demo.Model.User;
import com.example.demo.Repository.AdminRepository;
import com.example.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	UserRepository userRepository;

	@Autowired
	AdminRepository adminRepository;

	public static void main(String[] args) {

		SpringApplication.run(DemoApplication.class, args);
		System.out.println("Hello world");
	}

	@Override
	public void run(String... args) throws Exception {
//
//		User user = User.builder()
//				.username("raj")
//				.password(passwordEncoder.encode("raj123"))
//				.authority("adm")
//				.build();
//
//		user = userRepository.save(user);
//
//		Admin admin = Admin.builder()
//				.age(40)
//				.name("Raj Shukla")
//				.user(user)
//				.build();
//
//		adminRepository.save(admin);
	}

}
