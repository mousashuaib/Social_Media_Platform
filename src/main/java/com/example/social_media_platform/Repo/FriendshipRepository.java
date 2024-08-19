package com.example.social_media_platform.Repo;

import com.example.social_media_platform.Model.Entity.Friendship;
import com.example.social_media_platform.Model.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    // Find all friendships of a specific user
    List<Friendship> findByUserEntity1OrUserEntity2(UserEntity userEntity1, UserEntity userEntity2);

    // Find a friendship between two specific users
    Optional<Friendship> findByUserEntity1AndUserEntity2(UserEntity userEntity1, UserEntity userEntity2);
}
