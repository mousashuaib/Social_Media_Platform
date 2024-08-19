package com.example.social_media_platform.Controller;


import com.example.social_media_platform.Model.Dto.LikeDto;
import com.example.social_media_platform.Service.LikeServices;
import jdk.jfr.Description;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class LikeController {

    @Autowired
    private LikeServices likeServices;

    @Description("Allows the current user to like a specific post.")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PostMapping("/likePosts/{postId}")
    public ResponseEntity<LikeDto> likePost(@PathVariable Long postId) {
        LikeDto likeDto = likeServices.likePost(postId);
        return new ResponseEntity<>(likeDto, HttpStatus.CREATED);
    }

    @Description("Allows the current user to unlike a specific post they previously liked")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @DeleteMapping("/unlikePosts/{postId}")
    public ResponseEntity<Void> unlikePost(@PathVariable Long postId) {
        likeServices.unlikePost(postId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Description("Allows the current user to like a specific comment.")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PostMapping("/LikeComment/{commentId}")
    public ResponseEntity<LikeDto> likeComment(@PathVariable Long commentId) {
        LikeDto likeDto = likeServices.likeComment(commentId);
        return new ResponseEntity<>(likeDto, HttpStatus.CREATED);
    }

    @Description("Allows the current user to unlike a specific comment they previously liked.")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @DeleteMapping("/unlikeComment/{commentId}")
    public ResponseEntity<String> unlikeComment(@PathVariable Long commentId) {
        String message = likeServices.unlikeComment(commentId);
        return ResponseEntity.ok(message);
    }

}
