package com.example.social_media_platform.Service;

import com.example.social_media_platform.Model.Dto.PostDto;
import com.example.social_media_platform.Model.Entity.Media;
import com.example.social_media_platform.Model.Entity.Post;
import com.example.social_media_platform.Model.Mapper.PostMapper;
import com.example.social_media_platform.Repo.PostRepo;
import com.example.social_media_platform.Repo.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServices {
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private UserRepository userRepo;




    public PostDto createPost(PostDto postDto) {
        Post post = new Post();
        post.setUserEntity(userRepo.findById(postDto.getUserEntity()).orElseThrow(() -> new RuntimeException("User not found")));
        post.setText(postDto.getText());

        if (postDto.getMedia() != null) {
            postDto.getMedia().forEach(mediaDto -> {
                Media media = new Media();
                media.setMediaType(mediaDto.getMediaType());
                media.setMediaUrl(mediaDto.getMediaUrl());
                post.addMedia(media);
            });
        }

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
        Post exist = postRepo.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));
        postRepo.delete(exist);
    }

    public PostDto updatePost(Long postId, PostDto postDto) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));
        post.setText(postDto.getText());
        post.getMedia().clear();
        if (postDto.getMedia() != null) {
            postDto.getMedia().forEach(mediaDto -> {
                Media media = new Media();
                media.setMediaType(mediaDto.getMediaType());
                media.setMediaUrl(mediaDto.getMediaUrl());
                post.addMedia(media);
            });
        }
        Post updatedPost = postRepo.save(post);
        return postMapper.toDto(updatedPost);
    }



}
