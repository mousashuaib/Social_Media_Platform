package com.example.social_media_platform.Service;

import com.example.social_media_platform.Model.Dto.UserDto;
import com.example.social_media_platform.Model.Entity.Role;
import com.example.social_media_platform.Model.Entity.UserEntity;
import com.example.social_media_platform.Model.Mapper.UserMapper;
import com.example.social_media_platform.Repo.RoleRepository;
import com.example.social_media_platform.Repo.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    private final UserMapper userMapper;


    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

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
        if (user != null) {
            return user.get().getUserId();
        }
        throw new UsernameNotFoundException("User not found with email: " + email);
    }


}