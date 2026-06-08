package com.example.authsystem.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    @GetMapping("/admin")
    public String adminOnlyApi() {
        return "Welcome Admin. You can access this API.";
    }
}