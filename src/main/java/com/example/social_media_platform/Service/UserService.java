package com.example.social_media_platform.Service;

import com.example.social_media_platform.Model.Dto.PostDto;
import com.example.social_media_platform.Model.Dto.UserDto;
import com.example.social_media_platform.Model.Dto.UserDto2;
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
    private final CustomUserDetailsService customUserDetailsService;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder,
                       CustomUserDetailsService customUserDetailsService,
                       UserMapper userMapper){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.customUserDetailsService = customUserDetailsService;
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

    // Find user by email
    public Optional<UserEntity> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
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


    // Find user by ID
    public Optional<UserEntity> findUserById(Long userId) {
        return userRepository.findById(userId);
    }


    public List<UserDto> findUsersByIds(List<Long> ids) {
        List<UserEntity> users = userRepository.findAllById(ids);
        return users.stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }


    public List<UserDto2> getFriendSuggestions() {
        // Fetch the current user's ID using the custom user details service
        Long userId = customUserDetailsService.getCurrentUserId();

        // Fetch the user entity of the signed-in user
        UserEntity currentUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        // Get a list of IDs of the signed-in user's friends
        Set<Long> friendIds = currentUser.getFriendships() != null
                ? currentUser.getFriendships()
                .stream()
                .map(friendship -> friendship.getUserEntity2().getUserId())
                .collect(Collectors.toSet())
                : new HashSet<>(); // Use empty set if friendships are null

        // Add the current user's ID to the set to exclude from suggestions
        friendIds.add(userId);

        // Get a list of IDs of the users to whom the current user has sent friend requests
        Set<Long> sentRequestIds = currentUser.getSentRequests() != null
                ? currentUser.getSentRequests()
                .stream()
                .map(request -> request.getReceiver().getUserId())
                .collect(Collectors.toSet())
                : new HashSet<>(); // Use empty set if sent requests are null

        // Combine friend IDs and sent request IDs to form a complete exclusion list
        Set<Long> exclusionIds = new HashSet<>(friendIds);
        exclusionIds.addAll(sentRequestIds);

        // Fetch users that are not in the friends list or sent requests list
        List<UserEntity> suggestedUsers = userRepository.findUsersNotInIds(exclusionIds);
        if (suggestedUsers == null) {
            throw new RuntimeException("Suggested users list is null.");
        }

        // Convert the users to DTOs
        return suggestedUsers.stream()
                .map(userMapper::toDto2)
                .collect(Collectors.toList());
    }

}
