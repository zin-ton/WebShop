package com.example.WebShop.controllers;

import com.example.WebShop.models.Library;
import com.example.WebShop.models.Product;
import com.example.WebShop.models.User;
import com.example.WebShop.repositories.LibraryRepository;
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
    private LibraryRepository libraryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UsersRepository usersRepository;

    @PostMapping("/createProduct")
    private ResponseEntity<Object> createProduct(@RequestBody Product product){
        productRepository.save(product);
        return ResponseEntity.ok("product Added");
    }

    @PostMapping("/addProduct")
    private ResponseEntity<Object> addProduct(@RequestParam String productId, @RequestParam String userId){
        User user = usersRepository.findById(Long.valueOf(userId)).orElseThrow(() -> new RuntimeException("User not Found"));
        Product product = productRepository.findById(Long.valueOf(productId)).orElseThrow(() -> new RuntimeException("Product not Found"));
        user.getLibrary().getProducts().add(product);
        Library library = user.getLibrary();
        libraryRepository.save(library);
        usersRepository.save(user);
        return ResponseEntity.ok("added");
    }

    @GetMapping("/getLibrary")
    private ResponseEntity<Object> getLibrary(@RequestParam String userId){
        User user = usersRepository.findById(Long.valueOf(userId)).orElseThrow(() -> new RuntimeException("User not Found"));
        return ResponseEntity.ok(user.getLibrary().getProducts());
    }

}
