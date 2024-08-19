package com.example.social_media_platform.Controller;

import com.example.social_media_platform.Model.Dto.CommentDto;
import com.example.social_media_platform.Service.CommentServices;
import jdk.jfr.Description;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentServices commentServices;

    @Description("Endpoint to add a new comment to a post.")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<CommentDto> addComment(@RequestBody CommentDto commentDto) {
        CommentDto createdComment = commentServices.addComment(commentDto);
        return ResponseEntity.ok(createdComment);
    }

    @Description("Endpoint to get all comments.")
    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<CommentDto>> getAllComments() {
        List<CommentDto> comments = commentServices.getAllComments();
        return ResponseEntity.ok(comments);
    }

    @Description("Endpoint to get a comment by ID.")
    @GetMapping("/{commentId}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable Long commentId) {
        CommentDto commentDto = commentServices.getCommentById(commentId);
        return ResponseEntity.ok(commentDto);
    }

    @Description("Endpoint to delete a comment by ID.")
    @DeleteMapping("/{commentId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentServices.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }

    @Description("Endpoint to update a comment by ID.")
    @PutMapping("/{commentId}")
    @PreAuthorize("hasRole('USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Long commentId, @RequestBody CommentDto commentDto) {
        CommentDto updatedComment = commentServices.updateComment(commentId, commentDto);
        return ResponseEntity.ok(updatedComment);
    }
}
