package com.example.social_media_platform.Model.Dto;


import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class RoleDto {
    private Long role_id;
    private String name;
    private Set<UserDto> users;


}
