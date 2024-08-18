package com.example.social_media_platform.Controller;


import com.example.social_media_platform.Model.Entity.Friendship;
import com.example.social_media_platform.Service.FriendshipService;
import jdk.jfr.Description;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v0/friendship")
public class FriendshipController {

    @Autowired
    private FriendshipService friendshipService;

    @GetMapping("/user/{userId}")
    @Description("Get all friendships of a specific user")
    public ResponseEntity<List<Friendship>> getFriendships(@PathVariable Long userId) {
        List<Friendship> friendships = friendshipService.getFriendships(userId);
        return ResponseEntity.ok(friendships);
    }
}
