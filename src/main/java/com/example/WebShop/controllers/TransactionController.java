package com.example.WebShop.controllers;

import com.example.WebShop.models.User;
import com.example.WebShop.repositories.ProductRepository;
import com.example.WebShop.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TransactionController {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UsersRepository usersRepository;
    @PostMapping("/buy")
    private ResponseEntity<Object> buy(@RequestParam String userId){
        User user = usersRepository.findById(Long.valueOf(userId)).orElseThrow(() -> new RuntimeException("User not Found"));
        user.getProducts().addAll(user.getOrders());
        user.getOrders().clear();
        usersRepository.save(user);
        return ResponseEntity.ok("Bought");
    }
}
