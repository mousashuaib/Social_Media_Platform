package com.example.social_media_platform.Service;

import com.example.social_media_platform.Model.Dto.CommentDto;
import com.example.social_media_platform.Model.Entity.Comment;
import com.example.social_media_platform.Model.Entity.Post;
import com.example.social_media_platform.Model.Entity.UserEntity;
import com.example.social_media_platform.Model.Mapper.CommentMapper;
import com.example.social_media_platform.Repo.CommentRepo;
import com.example.social_media_platform.Repo.PostRepo;
import com.example.social_media_platform.Repo.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServices {


    private final CommentRepo commentRepo;
    private final CommentMapper commentMapper;
    private final CustomUserDetailsService customUserDetailsService;
    private final UserRepository userRepository;
    private final PostRepo postRepository;

    public CommentServices(CommentRepo commentRepo, CommentMapper commentMapper, CustomUserDetailsService customUserDetailsService, UserRepository userRepository, PostRepo postRepository) {
        this.commentRepo = commentRepo;
        this.commentMapper = commentMapper;
        this.customUserDetailsService = customUserDetailsService;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public CommentDto addComment(CommentDto commentDto) {
        Long currentUserId = customUserDetailsService.getCurrentUserId();

        // Ensure the comment text and post ID are present
        if (commentDto.getText() == null || commentDto.getPost() == null) {
            throw new IllegalArgumentException("Comment text and post ID are required");
        }

        // Find the post to which the comment is being added
        Post post = postRepository.findById(commentDto.getPost())
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));



        // Create and map the comment entity
        Comment comment = commentMapper.toEntity(commentDto);
        comment.setUser(userRepository.findById(currentUserId)
                .orElseThrow(() -> new EntityNotFoundException("User not found")));
        comment.setPost(post); // Link the comment to the post


        Comment savedComment = commentRepo.save(comment);

        post.getComments().add(savedComment);


        return commentMapper.toDto(savedComment);
    }

    public List<CommentDto> getAllComments() {
        return commentRepo.findAll().stream()
                .map(commentMapper::toDto)
                .collect(Collectors.toList());
    }

    public CommentDto getCommentById(Long commentId) {
        Comment comment = commentRepo.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found"));

        // No ownership check is needed here since this is a read operation accessible to all users

        return commentMapper.toDto(comment);
    }

    public void deleteComment(Long commentId) {
        Long currentUserId = customUserDetailsService.getCurrentUserId();
        Comment comment = commentRepo.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found"));

        // Ensure the comment belongs to the current user
        if (!comment.getUser().getUserId().equals(currentUserId)) {
            throw new AccessDeniedException("You are not authorized to delete this comment.");
        }

        commentRepo.delete(comment);
    }

    public CommentDto updateComment(Long commentId, CommentDto commentDto) {
        Long currentUserId = customUserDetailsService.getCurrentUserId();
        Comment comment = commentRepo.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found"));

        // Ensure the comment belongs to the current user
        if (!comment.getUser().getUserId().equals(currentUserId)) {
            throw new AccessDeniedException("You are not authorized to update this comment.");
        }

        // Perform the update
        comment.setText(commentDto.getText());
        Comment updatedComment = commentRepo.save(comment);
        return commentMapper.toDto(updatedComment);
    }
}
