package com.example.social_media_platform.Controller;

import com.example.social_media_platform.Model.Dto.UserDto;
import com.example.social_media_platform.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@RestController
@RequestMapping("/v0/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}/name")
    public ResponseEntity<String> getUserNameById(@PathVariable Long userId) {
        String name = userService.getUserNameById(userId);
        return ResponseEntity.ok(name);
    }

    @GetMapping("/{userId}/email")
    public ResponseEntity<String> getUserEmailById(@PathVariable Long userId) {
        String email = userService.getUserEmailById(userId);
        return ResponseEntity.ok(email);
    }

    @PutMapping("/{userId}/update")
    public ResponseEntity<Void> updateUser(
            @PathVariable Long userId,
            @RequestBody UserDto userDto) {
        userService.updateUser(userId, userDto.getName());
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/api/search/users")
    public ResponseEntity<List<UserDto>> searchUsersByName(@RequestParam String name) {
        logger.info("Received search request for name: {}", name);
        List<UserDto> users = userService.searchUsersByName(name);
        if (users.isEmpty()) {
            logger.info("No users found for name: {}", name);
            return ResponseEntity.noContent().build();
        }
        logger.info("Returning users: {}", users);
        return ResponseEntity.ok(users);
    }

}