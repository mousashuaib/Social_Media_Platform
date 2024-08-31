package com.example.social_media_platform.Controller;

import com.example.social_media_platform.Config.FileUploadService;
import com.example.social_media_platform.Model.Dto.ProfileDto;
import com.example.social_media_platform.Model.Dto.UserDto;
import com.example.social_media_platform.Model.Entity.UserEntity;
import com.example.social_media_platform.Service.CustomUserDetailsService;
import com.example.social_media_platform.Service.ProfileService;
import com.example.social_media_platform.Service.UserService;
import com.example.social_media_platform.Util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("v0/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;
    private final UserService userService;
    private final ProfileService profileService;

    private final FileUploadService fileUploadService;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, CustomUserDetailsService userDetailsService, UserService userService, ProfileService profileService, FileUploadService fileUploadService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.profileService = profileService;
        this.fileUploadService = fileUploadService;
    }





    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestParam String email, @RequestParam String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        String jwt = jwtUtil.generateToken(userDetails.getUsername());

        // Assuming you have a method to get the user ID from the user details
        Long userId = userService.getUserIdByEmail(email);

        Map<String, Object> response = new HashMap<>();
        response.put("token", jwt);
        response.put("userId", userId);

        return ResponseEntity.ok(response);
    }



    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestParam("name") String name,
                                      @RequestParam("email") String email,
                                      @RequestParam("password") String password,
                                      @RequestParam("defaultImage") MultipartFile defaultImage){
        UserEntity user = userService.registerUser(name, email, password);

        // Create a default profile image URL
        String defaultImageUrl;
        try {
            defaultImageUrl = fileUploadService.saveFile(defaultImage);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving default image");
        }

        // Create a profile automatically when registering a user with a default image
        ProfileDto profileDto = profileService.createProfile(user.getUserId(), defaultImageUrl);

        return ResponseEntity.ok("User registered and profile created successfully.");
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long userId) {
        UserDto userDto = userService.getUserById(userId);
        return ResponseEntity.ok(userDto);
    }

}

