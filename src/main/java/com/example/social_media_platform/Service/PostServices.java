package com.example.social_media_platform.Service;

import com.example.social_media_platform.Model.Dto.MediaDto;
import com.example.social_media_platform.Model.Dto.PostDto;
import com.example.social_media_platform.Model.Entity.Media;
import com.example.social_media_platform.Model.Entity.Post;
import com.example.social_media_platform.Model.Mapper.MediaMapper;
import com.example.social_media_platform.Model.Mapper.PostMapper;
import com.example.social_media_platform.Repo.MediaRepo;
import com.example.social_media_platform.Repo.PostRepo;
import com.example.social_media_platform.Repo.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PostServices {


    private final PostRepo postRepo;
    private final PostMapper postMapper;
    private final UserRepository userRepo;
    private final CustomUserDetailsService customUserDetailsService;
    private final MediaServices mediaServices;
    private final MediaMapper mediaMapper;

    public PostServices(
            PostRepo postRepo,
            PostMapper postMapper,
            UserRepository userRepo,
            CustomUserDetailsService customUserDetailsService,
            MediaServices mediaServices,
            MediaMapper mediaMapper
    ) {
        this.postRepo = postRepo;
        this.postMapper = postMapper;
        this.userRepo = userRepo;
        this.customUserDetailsService = customUserDetailsService;
        this.mediaServices = mediaServices;
        this.mediaMapper = mediaMapper;
    }

    public PostDto createPost(PostDto postDto) {
        Long currentUserId = customUserDetailsService.getCurrentUserId();

        // Check if the post's user ID matches the current user's ID
        if (!Objects.equals(postDto.getUserEntity(), currentUserId)) {
            throw new AccessDeniedException("You are not authorized to publish posts for other users.");
        }

        Post post = new Post();
        post.setUserEntity(userRepo.findById(currentUserId)
                .orElseThrow(() -> new EntityNotFoundException("User not found")));
        post.setText(postDto.getText());

        // Save the Post first so it is persistent
        Post savedPost = postRepo.save(post);

        return postMapper.toDto(savedPost);
    }


    public PostDto getPostById(Long postId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));
        return postMapper.toDto(post);
    }


    public List<PostDto> getAllPosts() {
        return postRepo.findAll().stream()
                .map(postMapper::toDto)
                .collect(Collectors.toList());
    }


    public void deletePost(Long postId) {
        Long currentUserId = customUserDetailsService.getCurrentUserId();
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));

        // Ensure that only the post owner or an admin can delete the post
        if (!Objects.equals(post.getUserEntity().getUserId(), currentUserId)) {
            throw new AccessDeniedException("You are not authorized to delete this post.");
        }

        postRepo.delete(post);
    }

    public PostDto updatePost(Long postId, PostDto postDto) {
        Long currentUserId = customUserDetailsService.getCurrentUserId();
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));

        if (!Objects.equals(post.getUserEntity().getUserId(), currentUserId)) {
            throw new AccessDeniedException("You are not authorized to update this post.");
        }

        // Update post text
        post.setText(postDto.getText());

        // Handle media update: Clear old media and add new/updated media
        if (postDto.getMedia() != null) {
            //delete the existing media
            mediaServices.deleteMedia(postId);

            // save or update the new media
            postDto.getMedia().forEach(mediaDto -> {
                mediaDto.setPost(postId); // Link media to the current post
                mediaServices.createMedia(mediaDto);
            });
        }

        Post updatedPost = postRepo.save(post);
        return postMapper.toDto(updatedPost);
    }



}
