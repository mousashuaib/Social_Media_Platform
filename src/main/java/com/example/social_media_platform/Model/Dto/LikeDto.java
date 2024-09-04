package com.example.social_media_platform.Model.Dto;

import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class LikeDto {
    private Long likeId;
    private UserDto2 userEntity;//here is the update from long to UserDto2
    private Long post;
    private Long comment;

}
