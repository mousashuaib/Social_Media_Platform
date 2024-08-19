package com.example.social_media_platform.Model.Dto;


import lombok.*;

import java.sql.Timestamp;


@Getter
@Setter
public class FriendRequestDto {
    private Long requestId;
    private Long sender;
    private Long receiver;
    private String status;
    private Timestamp createdAt;
}
