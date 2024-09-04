package com.example.social_media_platform.Model.Dto;


import com.example.social_media_platform.Model.Entity.UserEntity;
import lombok.*;

import java.util.Set;

@Getter
@Setter
public class UserDto {
    private Long userId;
    private String name;
    private String email;
    private Long profile;
    private String password;
    private Set<PostDto> posts;
    private Set<FriendRequestDto> sentRequests;
    private Set<FriendRequestDto> receivedRequests;
    private Set<CommentDto> comments;
    private Set<RoleDto> roles;
    private Set<FriendshipDto> friendships;


}
