package com.example.social_media_platform.Controller;

import com.example.social_media_platform.Model.Entity.UserEntity;
import com.example.social_media_platform.Service.CustomUserDetailsService;
import com.example.social_media_platform.Service.UserService;
import com.example.social_media_platform.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v0/auth")
public class AuthController {

    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

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
        return ResponseEntity.ok("User registered successfully");
    }


}

