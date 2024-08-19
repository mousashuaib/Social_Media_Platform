package com.example.social_media_platform.Service;

import com.example.social_media_platform.Model.Dto.CommentDto;
import com.example.social_media_platform.Model.Entity.Comment;
import com.example.social_media_platform.Model.Entity.UserEntity;
import com.example.social_media_platform.Model.Mapper.CommentMapper;
import com.example.social_media_platform.Repo.CommentRepo;
import com.example.social_media_platform.Repo.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServices {

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private UserRepository userRepository;

    public CommentDto addComment(CommentDto commentDto) {
        Long currentUserId = customUserDetailsService.getCurrentUserId();

        // Ensure that the comment is being created by the authenticated user
        if (!commentDto.getComment_id().equals(currentUserId)) {
            throw new AccessDeniedException("You are not authorized to create comments for other users.");
        }

        // Map the DTO to entity and set the associated user entity
        Comment comment = commentMapper.toEntity(commentDto);
        UserEntity user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        comment.setUser(user);

        Comment savedComment = commentRepo.save(comment);
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
