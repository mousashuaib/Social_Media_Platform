package com.example.social_media_platform.Model.Dto;


import lombok.*;

import java.sql.Timestamp;
@Getter
@Setter
public class FriendshipDto {
    private Long friendshipId;
    private Long userEntity1;
    private Long userEntity2;
    private Timestamp timestamp;
    private Timestamp lastUpdated;

}
