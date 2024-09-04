package com.example.social_media_platform.Model.Dto;


import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Set;

@Getter
@Setter
public class PostDto {
    private Long postId;
    private UserDto2 userEntity;
    private String text;
    private Timestamp date;
    private Set<MediaDto> media;
    private Set<CommentDto> comments;
    private Set<LikeDto> likes;
    private Timestamp lastUpdated;
}
