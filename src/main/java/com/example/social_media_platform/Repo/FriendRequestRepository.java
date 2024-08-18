package com.example.social_media_platform.Repo;


import com.example.social_media_platform.Model.Entity.FriendRequest;
import com.example.social_media_platform.Model.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

    // Find all friend requests received by a user
    List<FriendRequest> findByReceiver(UserEntity receiver);

    // Find all friend requests sent by a user
    List<FriendRequest> findBySender(UserEntity sender);

    // Find a friend request between specific sender and receiver
    FriendRequest findBySenderAndReceiver(UserEntity sender, UserEntity receiver);
}
