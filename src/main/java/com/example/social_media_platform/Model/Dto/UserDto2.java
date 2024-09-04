package com.example.social_media_platform.Model.Dto;


import com.example.social_media_platform.Model.Entity.Profile;
import lombok.*;

import java.util.Set;

@Getter
@Setter
public class UserDto2 {
    private Long userId;
    private String name;
    private ProfileDto profile;
    private Set<CommentDto> comments;//here is the problem
}
