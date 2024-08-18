package com.example.social_media_platform.Model.Mapper;

import com.example.social_media_platform.Model.Dto.UserDto;
import com.example.social_media_platform.Model.Entity.Profile;
import com.example.social_media_platform.Model.Entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {
        ProfileMapper.class,
        PostMapper.class,
        CommentMapper.class,
        FriendRequestMapper.class,
        FriendshipMapper.class
})
public interface UserMapper {

    @Mapping(source = "profile", target = "profile", qualifiedByName = "mapProfileToLong")
    @Mapping(target = "posts", source = "posts")
    @Mapping(target = "sentRequests", source = "sentRequests")
    @Mapping(target = "receivedRequests", source = "receivedRequests")
    @Mapping(target = "comments", source = "comments")
    @Mapping(target = "roles", source = "roles")
    @Mapping(target = "friendships", source = "friendships")
    UserDto toDto(UserEntity userEntity);

    @Mapping(source = "profile", target = "profile", qualifiedByName = "mapLongToProfile")
    @Mapping(target = "posts", source = "posts")
    @Mapping(target = "sentRequests", source = "sentRequests")
    @Mapping(target = "receivedRequests", source = "receivedRequests")
    @Mapping(target = "comments", source = "comments")
    @Mapping(target = "roles", source = "roles")
    @Mapping(target = "friendships", source = "friendships")
    UserEntity toEntity(UserDto userDto);

    @Named("mapLongToProfile")
    default Profile mapLongToProfile(Long profileId) {
        if (profileId == null) {
            return null;
        }
        Profile profile = new Profile();
        profile.setProfileId(profileId);
        return profile;
    }

    @Named("mapProfileToLong")
    default Long mapProfileToLong(Profile profile) {
        if (profile == null) {
            return null;
        }
        return profile.getProfileId();
    }
}
