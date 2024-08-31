package com.example.social_media_platform.Controller;

import com.example.social_media_platform.Model.Dto.UserDto;
import com.example.social_media_platform.Model.Entity.UserEntity;
import com.example.social_media_platform.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v0/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Endpoint to find user by email
    @GetMapping("/findByEmail")
    public ResponseEntity<?> findUserByEmail(@RequestParam String email) {
        Optional<UserEntity> userEntity = userService.findUserByEmail(email);
        if (userEntity.isPresent()) {
            return ResponseEntity.ok(userEntity.get());
        } else {
            return ResponseEntity.status(404).body("User not found with email: " + email);
        }
    }

    // Endpoint to find user by ID
    @GetMapping("/findById/{id}")
    public ResponseEntity<?> findUserById(@PathVariable Long id) {
        Optional<UserEntity> userEntity = userService.findUserById(id);
        if (userEntity.isPresent()) {
            return ResponseEntity.ok(userEntity.get());
        } else {
            return ResponseEntity.status(404).body("User not found with ID: " + id);
        }
    }
}
