package com.example.social_media_platform.Service;

import com.example.social_media_platform.Model.Entity.FriendRequest;
import com.example.social_media_platform.Model.Entity.FriendRequestStatus;
import com.example.social_media_platform.Model.Entity.Friendship;
import com.example.social_media_platform.Model.Entity.UserEntity;
import com.example.social_media_platform.Repo.FriendRequestRepository;
import com.example.social_media_platform.Repo.FriendshipRepository;
import com.example.social_media_platform.Repo.UserRepository;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class FriendRequestService {

    private final FriendRequestRepository friendRequestRepository;
    private final FriendshipRepository friendshipRepository;
    private final UserRepository userRepository;
    private final CustomUserDetailsService customUserDetailsService;

    public FriendRequestService(
            FriendRequestRepository friendRequestRepository,
            FriendshipRepository friendshipRepository,
            UserRepository userRepository,
            CustomUserDetailsService customUserDetailsService) {
        this.friendRequestRepository = friendRequestRepository;
        this.friendshipRepository = friendshipRepository;
        this.userRepository = userRepository;
        this.customUserDetailsService= customUserDetailsService;
    }


    // Send a friend request
    public FriendRequest sendFriendRequest(Long receiverId) {
        Long senderId = customUserDetailsService.getCurrentUserId(); // Get the current user ID
        UserEntity sender = userRepository.findById(senderId)
                .orElseThrow(() -> new IllegalArgumentException("Sender not found"));
        UserEntity receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new IllegalArgumentException("Receiver not found"));

        // Check if a friend request already exists even in reciprocal form
        FriendRequest existingRequest = friendRequestRepository.findBySenderAndReceiver(sender, receiver);
        FriendRequest existingRequest2 = friendRequestRepository.findBySenderAndReceiver(receiver, sender);
        if (existingRequest != null || existingRequest2 != null) {
            throw new IllegalArgumentException("Friend request already exists");
        }

        if (senderId.equals(receiverId)) {
            throw new IllegalArgumentException("You cannot send a friend request to yourself");
        }

        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setSender(sender);
        friendRequest.setReceiver(receiver);
        friendRequest.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        friendRequest.markAsPending();
        return friendRequestRepository.save(friendRequest);
    }

    // Accept a friend request
    public Friendship acceptFriendRequest(Long requestId, Long currentUserId) {
        FriendRequest request = friendRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Friend request not found"));

        // Ensure the current user is the receiver of the request
        if (!request.getReceiver().getUserId().equals(currentUserId)) {
            throw new IllegalArgumentException("You do not have permission to accept this friend request");
        }

        request.acceptRequest();
        friendRequestRepository.save(request);

        // Create friendship
        Friendship friendship = new Friendship();
        friendship.setUserEntity1(request.getSender());
        friendship.setUserEntity2(request.getReceiver());
        friendship.setTimestamp(new Timestamp(System.currentTimeMillis()));
        friendship.setLastUpdated(new Timestamp(System.currentTimeMillis()));
        return friendshipRepository.save(friendship);
    }

    // Reject a friend request
    public void rejectFriendRequest(Long requestId, Long currentUserId) {
        FriendRequest request = friendRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Friend request not found"));

        // Ensure the current user is the receiver of the request
        if (!request.getReceiver().getUserId().equals(currentUserId)) {
            throw new IllegalArgumentException("You do not have permission to reject this friend request");
        }

        request.rejectRequest();
        friendRequestRepository.save(request);
    }

    // Get all friend requests received by a user
    public List<FriendRequest> getReceivedFriendRequests(Long userId) {
        UserEntity receiver = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Filter friend requests to only include those with a PENDING status
        return friendRequestRepository.findByReceiverAndStatus(receiver, FriendRequestStatus.PENDING);
    }


    // Get all friend requests sent by a user
    public List<FriendRequest> getSentFriendRequests(Long userId) {
        UserEntity sender = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return friendRequestRepository.findBySender(sender);
    }

    // In FriendRequestService.java
    public void cancelFriendRequest(Long requestId, Long currentUserId) throws AccessDeniedException {
        FriendRequest friendRequest = friendRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Friend request not found"));

        // Check if the friend request is still pending and sent by the current user
        if (!friendRequest.getSender().getUserId().equals(currentUserId) || friendRequest.getStatus() != FriendRequestStatus.PENDING) {
            throw new AccessDeniedException("Cannot cancel this friend request.");
        }

        // Delete the friend request
        friendRequestRepository.delete(friendRequest);
    }

}
