package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

	@Value("${app.environment}")
    private String environment;

    @GetMapping("/api/profile")
    public String getActiveProfile() {
        return "Active Profile: " + environment;
    }
}
