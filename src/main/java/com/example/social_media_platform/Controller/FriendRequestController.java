package com.example.social_media_platform.Controller;

import com.example.social_media_platform.Model.Dto.FriendRequestDto;
import com.example.social_media_platform.Model.Dto.FriendshipDto;
import com.example.social_media_platform.Model.Entity.FriendRequest;
import com.example.social_media_platform.Model.Entity.Friendship;
import com.example.social_media_platform.Model.Mapper.FriendRequestMapper;
import com.example.social_media_platform.Model.Mapper.FriendshipMapper;
import com.example.social_media_platform.Service.CustomUserDetailsService;
import com.example.social_media_platform.Service.FriendRequestService;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/v0/friend-request")
public class FriendRequestController {

    private final FriendshipMapper friendshipMapper;
    private final FriendRequestMapper friendRequestMapper;
    private final FriendRequestService friendRequestService;
    private final CustomUserDetailsService customUserDetailsService;

    public FriendRequestController(
            FriendRequestService friendRequestService,
            FriendshipMapper friendshipMapper,
            FriendRequestMapper friendRequestMapper,
            CustomUserDetailsService CustomUserDetailsService
    ){
        this.friendRequestService = friendRequestService;
        this.friendshipMapper = friendshipMapper;
        this.friendRequestMapper = friendRequestMapper;
        this.customUserDetailsService = CustomUserDetailsService;
    }
    @PostMapping("/send")
    @PreAuthorize("hasAuthority('ROLE_USER')") // Ensure only authenticated users can send friend requests
    @Description("Send a friend request")
    public ResponseEntity<FriendRequestDto> sendFriendRequest(@RequestParam Long receiverId) {
        FriendRequest friendRequest = friendRequestService.sendFriendRequest(receiverId);
        return ResponseEntity.status(HttpStatus.CREATED).body(friendRequestMapper.toDto(friendRequest));
    }


    @PutMapping("/accept/{requestId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @Description("Accept a friend request")
    public ResponseEntity<FriendshipDto> acceptFriendRequest(@PathVariable Long requestId) throws AccessDeniedException {
        Long currentUserId = customUserDetailsService.getCurrentUserId();
        Friendship friendship = friendRequestService.acceptFriendRequest(requestId, currentUserId);
        return ResponseEntity.ok(friendshipMapper.toDto(friendship));
    }

    @PutMapping("/reject/{requestId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @Description("Reject a friend request")
    public ResponseEntity<Void> rejectFriendRequest(@PathVariable Long requestId) throws AccessDeniedException {
        Long currentUserId = customUserDetailsService.getCurrentUserId();
        friendRequestService.rejectFriendRequest(requestId, currentUserId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/received")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @Description("Get all received friend requests")
    public ResponseEntity<List<FriendRequestDto>> getReceivedFriendRequests() throws AccessDeniedException {
        Long userId = customUserDetailsService.getCurrentUserId();
        List<FriendRequest> receivedRequests = friendRequestService.getReceivedFriendRequests(userId);
        return ResponseEntity.ok(friendRequestMapper.toDtoList(receivedRequests));
    }

    @GetMapping("/sent")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @Description("Get all sent friend requests")
    public ResponseEntity<List<FriendRequestDto>> getSentFriendRequests() throws AccessDeniedException{
        Long userId = customUserDetailsService.getCurrentUserId();
        List<FriendRequest> sentRequests = friendRequestService.getSentFriendRequests(userId);
        return ResponseEntity.ok(friendRequestMapper.toDtoList(sentRequests));
    }

    // In FriendRequestController.java
    @DeleteMapping("/cancel/{requestId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @Description("Cancel a sent friend request if it is still pending")
    public ResponseEntity<Void> cancelFriendRequest(@PathVariable Long requestId) throws AccessDeniedException, java.nio.file.AccessDeniedException {
        Long currentUserId = customUserDetailsService.getCurrentUserId();
        friendRequestService.cancelFriendRequest(requestId, currentUserId);
        return ResponseEntity.noContent().build();
    }

}