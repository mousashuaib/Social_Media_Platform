package com.example.social_media_platform.Controller;


import com.example.social_media_platform.Model.Dto.PostDto;
import com.example.social_media_platform.Service.MediaServices;
import com.example.social_media_platform.Service.PostServices;
import jdk.jfr.Description;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("v0/post")
public class PostController {

    @Autowired
    private PostServices postServices;

    @Autowired
    private MediaServices mediaServices;


    @PostMapping("/AddPost")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto) {
        PostDto createdPost = postServices.createPost(postDto);

        if (postDto.getMedia() != null) {
            // Loop through each MediaDto and associate it with the saved Post
            postDto.getMedia().forEach(mediaDto -> {
                mediaDto.setPost(createdPost.getPostId()); // Associate the PostDto with MediaDto
                mediaServices.createMedia(mediaDto); // Save the Media entity
            });
        }

        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @Description("Retrieve a post by its ID. Accessible to all users.")
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/getBostById/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long postId) {
        PostDto postDto = postServices.getPostById(postId);
        return ResponseEntity.ok(postDto);
    }

    @Description("Retrieve all posts. Accessible to all users.")
    @GetMapping("/getAll")
    public ResponseEntity<List<PostDto>> getAllPosts() {
        List<PostDto> posts = postServices.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @Description("Delete a post. Only the owner of the post or an admin can delete it.")
    @DeleteMapping("/delete/{postId}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        postServices.deletePost(postId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Description("Update a post. Only the owner of the post or an admin can update it.")
    @PutMapping("/update/{postId}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<PostDto> updatePost(@PathVariable Long postId, @RequestBody PostDto postDto) {
        PostDto updatedPost = postServices.updatePost(postId, postDto);
        return ResponseEntity.ok(updatedPost);
    }

}
