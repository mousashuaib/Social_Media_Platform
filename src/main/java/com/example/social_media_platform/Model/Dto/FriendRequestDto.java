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

public class FriendRequestDto {
    private Long requestId;
    private Long sender;
    private Long receiver;
    private String status;
    private Timestamp createdAt;
}
