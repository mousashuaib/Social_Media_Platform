package com.example.social_media_platform.Model.Dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FriendshipDto {
    private Long friendshipId;
    private Long userEntity1;
    private Long userEntity2;
    private Timestamp timestamp;
    private Timestamp lastUpdated;

}
