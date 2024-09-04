package com.example.social_media_platform.Service;

import com.example.social_media_platform.Config.FileUploadService;
import com.example.social_media_platform.Model.Dto.MediaDto;
import com.example.social_media_platform.Model.Dto.PostDto;
import com.example.social_media_platform.Model.Entity.Post;
import com.example.social_media_platform.Model.Mapper.MediaMapper;
import com.example.social_media_platform.Model.Mapper.PostMapper;
import com.example.social_media_platform.Repo.PostRepo;
import com.example.social_media_platform.Repo.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    private final FileUploadService fileUploadService;

    @Autowired
    public PostServices(
            PostRepo postRepo,
            PostMapper postMapper,
            UserRepository userRepo,
            CustomUserDetailsService customUserDetailsService,
            MediaServices mediaServices,
            FileUploadService fileUploadService
    ) {
        this.postRepo = postRepo;
        this.postMapper = postMapper;
        this.userRepo = userRepo;
        this.customUserDetailsService = customUserDetailsService;
        this.mediaServices = mediaServices;
        this.fileUploadService = fileUploadService;
    }

    public PostDto createPost(String text, List<MultipartFile> media) throws IOException {
        Long currentUserId = customUserDetailsService.getCurrentUserId();

        Post post = new Post();
        post.setUserEntity(userRepo.findById(currentUserId)
                .orElseThrow(() -> new EntityNotFoundException("User not found")));
        post.setText(text);

        Post savedPost = postRepo.save(post);

        // Handle file uploads and save media information
        if (media != null && !media.isEmpty()) {
            for (MultipartFile file : media) {
                String fileName = fileUploadService.saveFile(file);
                System.out.println("File saved: " + fileName); // Debugging line
                MediaDto mediaDto = MediaDto.builder()
                        .post(savedPost.getPostId())
                        .mediaUrl("/uploads/" + fileName)
                        .mediaType(file.getContentType())
                        .file(file)
                        .build();
                mediaServices.createMedia(mediaDto);
            }
        }


        return postMapper.toDto(savedPost);
    }

    public PostDto updatePost(Long postId, String text, List<MultipartFile> mediaFiles) throws IOException {
        Long currentUserId = customUserDetailsService.getCurrentUserId();
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));

        if (!Objects.equals(post.getUserEntity().getUserId(), currentUserId)) {
            throw new AccessDeniedException("You are not authorized to update this post.");
        }

        post.setText(text);

        if (mediaFiles != null && !mediaFiles.isEmpty()) {
            // Remove existing media
            post.getMedia().clear(); // Clear the media collection
            postRepo.save(post); // Save changes to the post

            // Save new media
            for (MultipartFile file : mediaFiles) {
                String fileName = fileUploadService.saveFile(file);
                MediaDto mediaDto = MediaDto.builder()
                        .post(post.getPostId())  // Set the Post ID
                        .mediaUrl("/uploads/" + fileName)
                        .mediaType(file.getContentType())
                        .build();
                mediaServices.createMedia(mediaDto);
            }
        }

        Post updatedPost = postRepo.save(post);
        return postMapper.toDto(updatedPost);
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

        if (!Objects.equals(post.getUserEntity().getUserId(), currentUserId)) {
            throw new AccessDeniedException("You are not authorized to delete this post.");
        }

        postRepo.delete(post);
    }

}
