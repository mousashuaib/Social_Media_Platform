package com.example.social_media_platform.Model.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class UserDto {
    private Long userId;
    private String name;
    private String email;
    private Long profile;
    private String password;
    private Set<PostDto> posts;
    private Set<FriendRequestDto> sentRequests;
    private Set<FriendRequestDto> receivedRequests;
}
