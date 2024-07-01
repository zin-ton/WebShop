package com.example.WebShop.controllers;

import com.example.WebShop.models.Product;
import com.example.WebShop.models.Role;
import com.example.WebShop.models.User;
import com.example.WebShop.repositories.ProductRepository;
import com.example.WebShop.repositories.UsersRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@Controller
public class StoreController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired VerificationController verificationController;

    @PostMapping("/createProduct")
    private ResponseEntity<Object> createProduct(@RequestBody Product product, HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("userLogin")){
                    String userLogin = cookie.getValue();
                    User user = usersRepository.findByLogin(userLogin).orElseThrow(() -> new RuntimeException("User not found"));
                    if(user.getRole() == Role.ADMIN){
                        productRepository.save(product);
                        return ResponseEntity.ok("Product created");
                    }
                    else{
                        return ResponseEntity.badRequest().body("You are not an admin");
                    }
                }
            }
        }
        return ResponseEntity.badRequest().body("No userLogin cookie found");
    }

    @PostMapping("/addToCart")
    private ResponseEntity<Object> addProduct(@RequestParam String productId, HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("userLogin")){
                    String userLogin = cookie.getValue();
                    User user = usersRepository.findByLogin(userLogin).orElseThrow(() -> new RuntimeException("User not found"));
                    if(user.getRole() == Role.USER){
                        if(productRepository.getById(Long.valueOf(productId)) == null){
                            return ResponseEntity.badRequest().body("Product not found");
                        }
                        user.getOrders().add(Long.valueOf(productId));
                        usersRepository.save(user);
                        return ResponseEntity.ok("added");
                    }
                    else{
                        return ResponseEntity.badRequest().body("You are not a user");
                    }
                }
            }
        }
        return ResponseEntity.badRequest().body("No userLogin cookie found");
    }


    @GetMapping("/getProducts")
    private ResponseEntity<Object> getProducts(){
        return ResponseEntity.ok(productRepository.findAll());
    }

    @GetMapping("/getCart")
    private ResponseEntity<Object> getCart(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("userLogin")){
                    String userLogin = cookie.getValue();
                    User user = usersRepository.findByLogin(userLogin).orElseThrow(() -> new RuntimeException("User not found"));
                    return ResponseEntity.ok(user.getOrders());
                }
            }
        }
        return ResponseEntity.badRequest().body("No userLogin cookie found");
    }

    @GetMapping("/library")
    private ResponseEntity<Object> library(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("userLogin")){
                    String userLogin = cookie.getValue();
                    User user = usersRepository.findByLogin(userLogin).orElseThrow(() -> new RuntimeException("User not found"));
                    return ResponseEntity.ok(user.getProducts());
                }
            }
        }
        return ResponseEntity.badRequest().body("No userLogin cookie found");
    }

    @DeleteMapping("/deleteProduct")
    private ResponseEntity<Object> deleteProduct(@RequestParam String productId, HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("userLogin")){
                    String userLogin = cookie.getValue();
                    User user = usersRepository.findByLogin(userLogin).orElseThrow(() -> new RuntimeException("User not found"));
                    if(user.getRole() == Role.ADMIN){
                        productRepository.deleteById(Long.valueOf(productId));
                        return ResponseEntity.ok("Product deleted");
                    }
                    else{
                        return ResponseEntity.badRequest().body("You are not an admin");
                    }
                }
            }
        }
        return ResponseEntity.badRequest().body("No userLogin cookie found");
    }

    @DeleteMapping
    private ResponseEntity<Object> deleteFromCart(HttpServletRequest request, @RequestParam String productId){
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("userLogin")){
                    String userLogin = cookie.getValue();
                    User user = usersRepository.findByLogin(userLogin).orElseThrow(() -> new RuntimeException("User not found"));
                    List<Long> orders = user.getOrders();
                    if(orders.contains(Long.valueOf(productId))){
                        orders.remove(Long.valueOf(productId));
                        user.setOrders(orders);
                        usersRepository.save(user);
                        return ResponseEntity.ok("Product removed from cart");
                    }
                    else{
                        return ResponseEntity.badRequest().body("Product not found in cart");
                    }
                }
            }
        }
        return ResponseEntity.badRequest().body("No userLogin cookie found");
    }
    @GetMapping("/getProduct")
    private ResponseEntity<Object> getProduct(@RequestParam String productId){
        return ResponseEntity.ok(productRepository.getById(Long.valueOf(productId)));
    }


}
