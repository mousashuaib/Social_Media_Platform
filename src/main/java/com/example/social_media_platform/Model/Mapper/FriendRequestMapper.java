package com.example.social_media_platform.Model.Mapper;

import com.example.social_media_platform.Model.Dto.FriendRequestDto;
import com.example.social_media_platform.Model.Entity.FriendRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FriendRequestMapper {

    @Mapping(source = "sender.userId", target = "sender")
    @Mapping(source = "receiver.userId", target = "receiver")
    FriendRequestDto toDto(FriendRequest friendRequest);

    @Mapping(source = "sender", target = "sender.userId")
    @Mapping(source = "receiver", target = "receiver.userId")
    FriendRequest toEntity(FriendRequestDto friendRequestDto);
}
