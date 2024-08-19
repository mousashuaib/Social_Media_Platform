package com.example.social_media_platform.Model.Dto;

import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder // creates a builder for the class
public class LikeDto {
    private Long likeId;
    private Long userEntity;
    private Long post;
    private Long comment;

}
