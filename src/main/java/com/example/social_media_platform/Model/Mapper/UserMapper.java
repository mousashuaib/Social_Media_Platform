package com.example.social_media_platform.Model.Mapper;

import com.example.social_media_platform.Model.Dto.UserDto;
import com.example.social_media_platform.Model.Entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {PostMapper.class, FriendRequestMapper.class})
public interface UserMapper {

    @Mapping(source = "profile.profileId", target = "profile")
    UserDto toDto(UserEntity userEntity);

    @Mapping(source = "profile", target = "profile.profileId")
    UserEntity toEntity(UserDto userDto);
}
