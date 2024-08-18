package com.example.social_media_platform.Controller;


import com.example.social_media_platform.Model.Dto.PostDto;
import com.example.social_media_platform.Service.PostServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostServices postServices ;

    @PostMapping("/AddPost")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto) {
        PostDto createdPost = postServices.createPost(postDto);
        return ResponseEntity.ok(createdPost);
    }

    @GetMapping("/returnPost/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long postId) {
        PostDto postDto = postServices.getPostById(postId);
        return ResponseEntity.ok(postDto);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<PostDto>> getAllPosts() {
        List<PostDto> posts = postServices.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @DeleteMapping("delete/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        postServices.deletePost(postId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("update/{postId}")
    public ResponseEntity<PostDto> updatePost(@PathVariable Long postId, @RequestBody PostDto postDto) {
        PostDto updatedPost = postServices.updatePost(postId, postDto);
        return ResponseEntity.ok(updatedPost);
    }

}
