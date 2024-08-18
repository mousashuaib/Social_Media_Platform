package com.example.social_media_platform.Service;

import com.example.social_media_platform.Model.Entity.Friendship;
import com.example.social_media_platform.Model.Entity.UserEntity;
import com.example.social_media_platform.Repo.FriendshipRepository;
import com.example.social_media_platform.Repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendshipService {


    private final FriendshipRepository friendshipRepository;
    private final UserRepository userRepository;

    public FriendshipService(
            FriendshipRepository friendshipRepository,
            UserRepository userRepository) {
        this.friendshipRepository = friendshipRepository;
        this.userRepository = userRepository;
    }


    // Get all friendships of a specific user
    public List<Friendship> getFriendships(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return friendshipRepository.findByUserEntity1OrUserEntity2(user, user);
    }
}
