package com.example.social_media_platform.Model.Dto;


import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder // creates a builder for the class
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDto {
    private Long profileId;
    private Long userEntity;
    private String bio;
    private String profilePictureUrl;
    private String coverPictureUrl;
    private Timestamp createdAt;
    private String misc;
    private Timestamp lastUpdated;

}
