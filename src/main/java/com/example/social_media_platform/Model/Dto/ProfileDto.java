package com.example.social_media_platform.Model.Dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDto {
    private Long profileId;
    private Long userEntity;
    private String bio;
    private String profilePictureUrl;
    private Timestamp createdAt;
    private String misc;
    private Timestamp lastUpdated;

}
