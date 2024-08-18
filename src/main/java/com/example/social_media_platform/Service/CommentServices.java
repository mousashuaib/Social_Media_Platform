package com.example.social_media_platform.Service;

import com.example.social_media_platform.Model.Dto.CommentDto;
import com.example.social_media_platform.Model.Entity.Comment;
import com.example.social_media_platform.Model.Mapper.CommentMapper;
import com.example.social_media_platform.Repo.CommentRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServices {
    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private CommentMapper commentMapper;

    public CommentDto addComment(CommentDto commentDto) {
        Comment comment = commentMapper.toEntity(commentDto);
        Comment savedComment = commentRepo.save(comment);
        return commentMapper.toDto(savedComment);
    }


    public List<CommentDto> getAllComments() {
        return commentRepo.findAll().stream()
                .map(commentMapper::toDto)
                .collect(Collectors.toList());



    }
    public CommentDto getCommentById(Long commentId){
    Comment comment = commentRepo.findById(commentId).orElseThrow(() -> new EntityNotFoundException("الكومنت غير موجود حبيبي"));
        System.out.println("Number of likes for comment: " + comment.getLikes().size());

        return commentMapper.toDto(comment);
    }



    public void deleteComment(Long commentId) {
        Comment comment = commentRepo.findById(commentId).
                orElseThrow(() -> new EntityNotFoundException("الكومنت غير موجود حبيبي"));
        commentRepo.delete(comment);
    }

    public CommentDto updateComment(Long commentId, CommentDto commentDto) {
        Comment comment = commentRepo.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("الكومنت غير موجود حبيبي"));
        comment.setText(commentDto.getText());
        Comment updatedComment = commentRepo.save(comment);
        return commentMapper.toDto(updatedComment);
    }



}
