package com.example.social_media_platform.Controller;


import com.example.social_media_platform.Model.Dto.CommentDto;
import com.example.social_media_platform.Model.Dto.PostDto;
import com.example.social_media_platform.Service.CommentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    private CommentServices commentServices;

    @PostMapping("/AddComm")
    public ResponseEntity<CommentDto> addComment(@RequestBody CommentDto commentDto) {
        CommentDto createdComment = commentServices.addComment(commentDto);
        return ResponseEntity.ok(createdComment);
    }

    @GetMapping("/getAllComment")
    public ResponseEntity<List<CommentDto>> getAllComments() {
        List<CommentDto> comment = commentServices.getAllComments();
        return ResponseEntity.ok(comment);
    }

    @GetMapping("getByIdForComment/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable Long commentId) {
        CommentDto commentDto = commentServices.getCommentById(commentId);
        return ResponseEntity.ok(commentDto);
    }

    @DeleteMapping("deleteById/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentServices.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/updatee/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Long commentId, @RequestBody CommentDto commentDto) {
        CommentDto updatedComment = commentServices.updateComment(commentId, commentDto);
        return ResponseEntity.ok(updatedComment);
    }
}
