package com.example.social_media_platform.Service;

import com.example.social_media_platform.Model.Dto.UserDto;
import com.example.social_media_platform.Model.Entity.Role;
import com.example.social_media_platform.Model.Entity.UserEntity;
import com.example.social_media_platform.Model.Mapper.UserMapper;
import com.example.social_media_platform.Repo.RoleRepository;
import com.example.social_media_platform.Repo.UserRepository;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
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

    public UserDto getUserById(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return userMapper.toDto(user); // Use a mapper to convert UserEntity to UserDto
    }

    public Long getUserIdByEmail(String email) {
        Optional<UserEntity> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            return user.get().getUserId();
        }
        throw new UsernameNotFoundException("User not found with email: " + email);
    }

    // New methods to get user name and email
    public String getUserNameById(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getName();
    }

    public String getUserEmailById(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getEmail();
    }

    // Method to update user details
    public void updateUser(Long userId, String name) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setName(name);
        userRepository.save(user);
    }


    public List<UserDto> searchUsersByName(String name) {
        logger.info("Searching for users with name: {}", name);
        List<UserEntity> users = userRepository.findByNameContainingIgnoreCase(name);
        logger.info("Found users: {}", users);
        return users.stream()
                .map(user -> {
                    UserDto dto = new UserDto();
                    dto.setUserId(user.getUserId());
                    dto.setName(user.getName());
                    return dto;
                })
                .toList();
    }

}
