package com.example.WebShop.controllers;

import com.example.WebShop.models.Product;
import com.example.WebShop.models.User;
import com.example.WebShop.repositories.ProductRepository;
import com.example.WebShop.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StoreController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UsersRepository usersRepository;

    @PostMapping("/createProduct")
    private ResponseEntity<Object> createProduct(@RequestBody Product product){
        productRepository.save(product);
        return ResponseEntity.ok("product Added");
    }

    @PostMapping("/addToCart")
    private ResponseEntity<Object> addProduct(@RequestParam String productId, @RequestParam String userId){
        User user = usersRepository.findById(Long.valueOf(userId)).orElseThrow(() -> new RuntimeException("User not Found"));
        if(productRepository.getById(Long.valueOf(productId)) == null){
            return ResponseEntity.badRequest().body("Product not found");
        }
        user.getOrders().add(Long.valueOf(productId));
        usersRepository.save(user);
        return ResponseEntity.ok("added");
    }

    @GetMapping("/getLibrary")
    private ResponseEntity<Object> getLibrary(@RequestParam String userId){
        User user = usersRepository.findById(Long.valueOf(userId)).orElseThrow(() -> new RuntimeException("User not Found"));
        return ResponseEntity.ok(user.getProducts());
    }

    @GetMapping("/getProducts")
    private ResponseEntity<Object> getProducts(){
        return ResponseEntity.ok(productRepository.findAll());
    }

    @GetMapping("/getCart")
    private ResponseEntity<Object> getCart(@RequestParam String userId){
        User user = usersRepository.findById(Long.valueOf(userId)).orElseThrow(() -> new RuntimeException("User not Found"));
        return ResponseEntity.ok(user.getOrders());
    }

    @GetMapping("/library")
    private ResponseEntity<Object> library(@RequestParam String userId){
        User user = usersRepository.findById(Long.valueOf(userId)).orElseThrow(() -> new RuntimeException("User not Found"));
        return ResponseEntity.ok(user.getProducts());
    }


}
