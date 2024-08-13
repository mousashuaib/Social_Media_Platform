package com.example.social_media_platform.Model.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private Long commentId;
    private Long userId;
    private Long postId;
    private String text;
    private Timestamp date;
    private Set<LikeDto> likes;


}
