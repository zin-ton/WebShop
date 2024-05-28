package com.example.WebShop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@Controller
public class VerificationController {
    public boolean verifyProduct() {
        return true;
    }
}
