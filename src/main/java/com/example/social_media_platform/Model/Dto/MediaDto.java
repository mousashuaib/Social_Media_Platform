package com.example.social_media_platform.Model.Dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
public class MediaDto {
    private Long mediaId;
    private Long post;
    private String mediaType;
    private String mediaUrl;
    private MultipartFile file; // To handle file upload
}
