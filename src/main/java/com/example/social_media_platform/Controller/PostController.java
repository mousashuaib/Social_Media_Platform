package com.example.social_media_platform.Controller;

import com.example.social_media_platform.Model.Dto.MediaDto;
import com.example.social_media_platform.Model.Dto.PostDto;
import com.example.social_media_platform.Model.Dto.UserDto;
import com.example.social_media_platform.Service.MediaServices;
import com.example.social_media_platform.Service.PostServices;
import com.example.social_media_platform.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("v0/post")
public class PostController {

    @Autowired
    private PostServices postServices;

    @Autowired
    private MediaServices mediaServices;

    @Autowired
    private UserService userService;

    @PostMapping("/AddPost")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<PostDto> createPost(@RequestParam("content") String text,
                                              @RequestParam(value = "files", required = false) List<MultipartFile> files) throws IOException {
        PostDto createdPost = postServices.createPost(text, files);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @GetMapping("/getPostById/{postId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long postId) {
        PostDto postDto = postServices.getPostById(postId);
        return ResponseEntity.ok(postDto);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<PostDto>> getAllPosts() {
        List<PostDto> posts = postServices.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/getAllPosts")
    public List<PostDto> getAll() {
        return postServices.getAllPosts();
    }




    @DeleteMapping("/delete/{postId}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        postServices.deletePost(postId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update/{postId}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<PostDto> updatePost(@PathVariable Long postId,
                                              @RequestParam("text") String text,
                                              @RequestParam(value = "files", required = false) List<MultipartFile> files) throws IOException {
        PostDto updatedPost = postServices.updatePost(postId, text, files);
        return ResponseEntity.ok(updatedPost);
    }

}

