package com.example.social_media_platform.Model.Mapper;

import com.example.social_media_platform.Model.Dto.UserDto;
import com.example.social_media_platform.Model.Dto.UserDto2;
import com.example.social_media_platform.Model.Entity.Profile;
import com.example.social_media_platform.Model.Entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = {
        ProfileMapper.class,
        PostMapper.class,
        CommentMapper.class,  // Ensure CommentMapper is used for converting comments
        FriendRequestMapper.class,
        FriendshipMapper.class
}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    // Existing mapping methods
    @Mapping(source = "profile", target = "profile", qualifiedByName = "mapProfileToLong")
    UserDto toDto(UserEntity userEntity);

    @Mapping(source = "profile", target = "profile", qualifiedByName = "mapLongToProfile")
    UserEntity toEntity(UserDto userDto);

    // New method to map UserEntity to UserDto2
    @Mapping(source = "profile", target = "profile")
    @Mapping(source = "comments", target = "comments") // Maps comments using CommentMapper
    UserDto2 toDto2(UserEntity userEntity);

    // Helper methods for Profile mapping
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
