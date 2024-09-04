package com.example.social_media_platform.Controller;

import com.example.social_media_platform.Model.Dto.UserDto;
import com.example.social_media_platform.Model.Dto.UserDto2;
import com.example.social_media_platform.Model.Entity.UserEntity;
import com.example.social_media_platform.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
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

    // Endpoint to find users by IDs
    @GetMapping("/findByIds")
    public ResponseEntity<List<UserDto>> findUsersByIds(@RequestParam List<Long> ids) {
        List<UserDto> users = userService.findUsersByIds(ids);
        return ResponseEntity.ok(users);
    }


    // Endpoint to fetch users who are not friends with the signed-in user
    @GetMapping("/suggestions")
    public ResponseEntity<List<UserDto2>> getFriendSuggestions() {
        List<UserDto2> suggestions = userService.getFriendSuggestions();
        return ResponseEntity.ok(suggestions);
    }

}
