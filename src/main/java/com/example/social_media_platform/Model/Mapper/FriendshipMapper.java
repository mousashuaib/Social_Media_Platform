package com.example.social_media_platform.Model.Mapper;

import com.example.social_media_platform.Model.Dto.FriendshipDto;

import com.example.social_media_platform.Model.Entity.Friendship;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FriendshipMapper {

    @Mapping(source = "userEntity1.userId", target = "userEntity1")
    @Mapping(source = "userEntity2.userId", target = "userEntity2")
    FriendshipDto toDto(Friendship friendship);

    @Mapping(source = "userEntity1", target = "userEntity1.userId")
    @Mapping(source = "userEntity2", target = "userEntity2.userId")
    Friendship toEntity(FriendshipDto friendshipDto);
}
