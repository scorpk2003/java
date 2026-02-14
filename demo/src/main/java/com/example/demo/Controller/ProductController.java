package com.example.demo.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
    @GetMapping("/hello")
    String sayHello(){
        return "First Spring Boot";
    }
    @GetMapping("/sayhi")
    String sayHi(){
        return "hi";
    }
}
