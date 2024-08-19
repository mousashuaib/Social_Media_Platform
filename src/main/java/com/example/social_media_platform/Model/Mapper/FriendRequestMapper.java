package com.example.social_media_platform.Model.Mapper;

import com.example.social_media_platform.Model.Dto.FriendRequestDto;
import com.example.social_media_platform.Model.Entity.FriendRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FriendRequestMapper {

    @Mapping(source = "sender.userId", target = "sender")
    @Mapping(source = "receiver.userId", target = "receiver")
    @Mapping(source = "status", target = "status")
    FriendRequestDto toDto(FriendRequest friendRequest);

    @Mapping(source = "sender", target = "sender.userId")
    @Mapping(source = "receiver", target = "receiver.userId")
    @Mapping(source = "status", target = "status")
    FriendRequest toEntity(FriendRequestDto friendRequestDto);



    List<FriendRequestDto> toDtoList(List<FriendRequest> receivedRequests);
}
