package com.example.social_media_platform.Model.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private Long comment_id;
    private Long user;
    private Long post;
    private String text;
    private Timestamp date;
    private Set<LikeDto> likes = new HashSet<>();;


}
