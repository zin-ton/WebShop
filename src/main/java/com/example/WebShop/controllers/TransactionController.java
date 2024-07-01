package com.example.WebShop.controllers;

import com.example.WebShop.models.User;
import com.example.WebShop.repositories.ProductRepository;
import com.example.WebShop.repositories.UsersRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
@CrossOrigin
@Controller
public class TransactionController {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UsersRepository usersRepository;
    @PostMapping("/buy")
    public ResponseEntity<Object> buy(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("userLogin")) {
                    String userLogin = cookie.getValue();
                    User user = usersRepository.findByLogin(userLogin)
                            .orElseThrow(() -> new RuntimeException("User not found"));
                    if (!user.getOrders().isEmpty()) {
                        user.getProducts().addAll(user.getOrders());
                        user.getOrders().clear();
                        usersRepository.save(user);
                        return ResponseEntity.ok("Bought");
                    } else {
                        return ResponseEntity.badRequest().body("No products to buy");
                    }
                }
            }
        }
        return ResponseEntity.badRequest().body("No userLogin cookie found");
    }
}
