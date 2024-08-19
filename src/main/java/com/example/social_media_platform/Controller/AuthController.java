package com.example.social_media_platform.Controller;

import com.example.social_media_platform.Model.Dto.ProfileDto;
import com.example.social_media_platform.Model.Entity.UserEntity;
import com.example.social_media_platform.Service.CustomUserDetailsService;
import com.example.social_media_platform.Service.ProfileService;
import com.example.social_media_platform.Service.UserService;
import com.example.social_media_platform.Util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v0/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;
    private final UserService userService;
    private final ProfileService profileService;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, CustomUserDetailsService userDetailsService, UserService userService, ProfileService profileService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.profileService = profileService;
    }




    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(email); // Use CustomUserDetailsService
        String jwt = jwtUtil.generateToken(userDetails.getUsername());

        return ResponseEntity.ok("token: " + jwt);
    }



    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestParam String name, @RequestParam String email, @RequestParam String password) {
        UserEntity user = userService.registerUser(name, email, password);
        ProfileDto profileDto = profileService.createProfile(user.getUserId()); // Create a profile automatically when registering a user
        return ResponseEntity.ok("User registered and profile created successfully. User Data:");


    }


}

