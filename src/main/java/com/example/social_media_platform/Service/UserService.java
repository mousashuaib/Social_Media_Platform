package com.example.social_media_platform.Service;

import com.example.social_media_platform.Model.Dto.UserDto;
import com.example.social_media_platform.Model.Entity.Role;
import com.example.social_media_platform.Model.Entity.UserEntity;
import com.example.social_media_platform.Model.Mapper.UserMapper;
import com.example.social_media_platform.Repo.RoleRepository;
import com.example.social_media_platform.Repo.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private  UserMapper userMapper ;
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Method to register a user
    public UserEntity registerUser(String name, String email, String password) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("User with this email already exists");
        }

        // Create a new user entity
        UserEntity user = new UserEntity();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));

        // Set default role (e.g., ROLE_USER)
        Set<Role> roles = new HashSet<>();
        Role defaultRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Default role not found"));
        roles.add(defaultRole);
        user.setRoles(roles);

        // Save the user
        return userRepository.save(user);
    }

    // Find user by email
    public Optional<UserEntity> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Find user by ID
    public Optional<UserEntity> findUserById(Long userId) {
        return userRepository.findById(userId);
    }
}
