package com.example.social_media_platform.Controller;


import com.example.social_media_platform.Model.Dto.LikeDto;
import com.example.social_media_platform.Service.LikeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class LikeController {

    @Autowired
    private LikeServices likeServices;



    @PostMapping("/likePosts/{postId}")
    public ResponseEntity<LikeDto> likePost(@PathVariable Long postId, @RequestParam Long userId) {
        LikeDto likeDto = likeServices.likePost(postId, userId);
        return new ResponseEntity<>(likeDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/unlikePosts/{postId}/")
    public ResponseEntity<Void> unlikePost(@PathVariable Long postId, @RequestParam Long userId) {
        likeServices.unlikePost(postId, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PostMapping("LikeComment/{commentId}")
    public ResponseEntity<LikeDto> likeComment(@PathVariable Long commentId, @RequestParam Long userId) {
        LikeDto message = likeServices.likeComment(commentId, userId);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @DeleteMapping("unlikeComment/{commentId}/like")
    public ResponseEntity<String> unlikeComment(@PathVariable Long commentId, @RequestParam Long userId) {
        String message = likeServices.unlikeComment(commentId, userId);
        return ResponseEntity.ok(message);
    }

}
