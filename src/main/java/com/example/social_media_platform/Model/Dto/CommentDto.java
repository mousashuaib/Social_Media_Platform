package com.example.social_media_platform.Model.Dto;

import lombok.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class CommentDto {
    private Long comment_id;
    private Long user;
    private Long post;
    private String text;
    private Timestamp date;
    private Set<LikeDto> likes = new HashSet<>();

}
