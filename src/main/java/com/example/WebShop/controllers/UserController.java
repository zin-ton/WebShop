package com.example.WebShop.controllers;

import com.example.WebShop.models.User;
import com.example.WebShop.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserController {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user){
        if(usersRepository.findByLogin(user.getLogin()).isPresent()){
            return ResponseEntity.badRequest().body("Username already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
        return ResponseEntity.ok("User Created");
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody User requestUser){
        User user = usersRepository.findByLogin(requestUser.getLogin()).orElseThrow(() -> new RuntimeException("User not found"));
        if(!passwordEncoder.matches(requestUser.getPassword(), user.getPassword())){
            return ResponseEntity.badRequest().body("Invalid password");
        }
        else{
            return ResponseEntity.ok(user);
        }
    }
}
