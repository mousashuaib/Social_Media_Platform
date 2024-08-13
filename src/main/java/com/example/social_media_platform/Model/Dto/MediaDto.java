package com.example.social_media_platform.Model.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MediaDto {
    private Long mediaId;
    private Long postId;
    private String mediaType;
    private String mediaUrl;
}
