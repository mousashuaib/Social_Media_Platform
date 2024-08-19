package com.example.social_media_platform.Model.Dto;

import lombok.*;

@Getter
@Setter
@Builder
public class MediaDto {
    private Long mediaId;
    private Long post;
    private String mediaType;
    private String mediaUrl;
}
