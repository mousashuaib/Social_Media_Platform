package com.example.social_media_platform.Service;

import com.example.social_media_platform.Model.Dto.LikeDto;
import com.example.social_media_platform.Model.Entity.Comment;
import com.example.social_media_platform.Model.Entity.Like;
import com.example.social_media_platform.Model.Entity.Post;
import com.example.social_media_platform.Model.Entity.UserEntity;
import com.example.social_media_platform.Model.Mapper.LikeMapper;
import com.example.social_media_platform.Repo.CommentRepo;
import com.example.social_media_platform.Repo.LikeRepo;
import com.example.social_media_platform.Repo.PostRepo;
import com.example.social_media_platform.Repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class LikeServices {

    @Autowired
    private LikeRepo likeRepo;

    @Autowired
    private LikeMapper likeMapper;

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    public LikeDto likePost(Long postId) {
        Long currentUserId = customUserDetailsService.getCurrentUserId();
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        UserEntity userEntity = userRepository.findById(currentUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Optional<Like> existingLike = likeRepo.findByPostAndUserEntity(post, userEntity);
        if (existingLike.isPresent()) {
            throw new AccessDeniedException("You have already liked this post.");
        }


        if (!Objects.equals(userEntity.getUserId(), currentUserId)) {
            throw new AccessDeniedException("You dont have permission to like this post.");
        }



        Like like = new Like();
        like.setPost(post);
        like.setUserEntity(userEntity);

        Like savedLike = likeRepo.save(like);
        return likeMapper.toDto(savedLike);
    }

    public void unlikePost(Long postId) {
        Long currentUserId = customUserDetailsService.getCurrentUserId();
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        UserEntity user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Like like = likeRepo.findByPostAndUserEntity(post, user)
                .orElseThrow(() -> new RuntimeException("Like not found"));

        if (!Objects.equals(user.getUserId(), currentUserId)) {
            throw new AccessDeniedException("You dont have permission to unlike this post.");
        }

        likeRepo.delete(like);
    }

    public LikeDto likeComment(Long commentId) {
        Long currentUserId = customUserDetailsService.getCurrentUserId();
        Comment comment = commentRepo.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        UserEntity user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Optional<Like> existingLike = likeRepo.findByCommentAndUserEntity(comment, user);
        if (existingLike.isPresent()) {
            throw new AccessDeniedException("You have already liked this comment.");
        }

        if (!Objects.equals(user.getUserId(), currentUserId)) {
            throw new AccessDeniedException("You dont have permission to unlike this post.");
        }

        Like like = new Like();
        like.setComment(comment);
        like.setUserEntity(user);

        Like savedLike = likeRepo.save(like);
        return likeMapper.toDto(savedLike);
    }

    public String unlikeComment(Long commentId) {
        Long currentUserId = customUserDetailsService.getCurrentUserId();
        Comment comment = commentRepo.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        UserEntity user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Like like = likeRepo.findByCommentAndUserEntity(comment, user)
                .orElseThrow(() -> new RuntimeException("Like not found"));

        if (!Objects.equals(user.getUserId(), currentUserId)) {
            throw new AccessDeniedException("You dont have permission to unlike this post.");
        }

        likeRepo.delete(like);

        return "Comment successfully unliked.";
    }

    public Optional<Like> isPostLiked(Long postId) {
        Long currentUserId = customUserDetailsService.getCurrentUserId();
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        UserEntity user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Optional<Like> existingLike = likeRepo.findByPostAndUserEntity(post, user);
        return existingLike;
    }

}
