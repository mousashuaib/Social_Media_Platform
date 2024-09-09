package com.example.social_media_platform.Model.Dto;


import lombok.*;

import java.sql.Timestamp;


@Getter
@Setter
public class FriendRequestDto {
    private Long requestId;
    private UserDto2 sender;//here is the update to show the image and username in the frontEnd
    private UserDto2 receiver;
    private String status;
    private Timestamp createdAt;
}
