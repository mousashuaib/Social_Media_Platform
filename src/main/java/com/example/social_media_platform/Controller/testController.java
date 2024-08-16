package com.example.social_media_platform.Controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v0/test")
public class testController {

    @GetMapping("/")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok("Test successful");
    }
}
