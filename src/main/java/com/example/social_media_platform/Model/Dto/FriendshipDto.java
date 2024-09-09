package com.example.social_media_platform.Model.Dto;


import lombok.*;

import java.sql.Timestamp;
@Getter
@Setter
public class FriendshipDto {
    private Long friendshipId;
    private UserDto2 userEntity1;//here is the change
    private UserDto2 userEntity2;//here is the change
    private Timestamp timestamp;
    private Timestamp lastUpdated;
}
