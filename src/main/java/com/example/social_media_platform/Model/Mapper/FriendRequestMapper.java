package com.example.social_media_platform.Model.Mapper;

import com.example.social_media_platform.Model.Dto.FriendRequestDto;
import com.example.social_media_platform.Model.Dto.ProfileDto;
import com.example.social_media_platform.Model.Dto.UserDto2;
import com.example.social_media_platform.Model.Entity.FriendRequest;
import com.example.social_media_platform.Model.Entity.Profile;
import com.example.social_media_platform.Model.Entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import java.util.List;

@Mapper(componentModel = "spring")
public interface FriendRequestMapper {

    // Update mappings for sender and receiver to use the correct helper methods
    @Mapping(source = "sender", target = "sender", qualifiedByName = "mapUserEntityToDto")
    @Mapping(source = "receiver", target = "receiver", qualifiedByName = "mapUserEntityToDto")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "createdAt", target = "createdAt")
    FriendRequestDto toDto(FriendRequest friendRequest);

    @Mapping(source = "sender", target = "sender", qualifiedByName = "mapUserDtoToEntity")
    @Mapping(source = "receiver", target = "receiver", qualifiedByName = "mapUserDtoToEntity")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "createdAt", target = "createdAt")
    FriendRequest toEntity(FriendRequestDto friendRequestDto);

    List<FriendRequestDto> toDtoList(List<FriendRequest> receivedRequests);

    // Helper method to map UserEntity to UserDto2
    @Named("mapUserEntityToDto")
    default UserDto2 mapUserEntityToDto(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }
        UserDto2 userDto = new UserDto2();
        userDto.setUserId(userEntity.getUserId());
        userDto.setName(userEntity.getName());
        userDto.setProfile(mapProfileToDto(userEntity.getProfile()));
        return userDto;
    }

    // Helper method to map UserDto2 to UserEntity
    @Named("mapUserDtoToEntity")
    default UserEntity mapUserDtoToEntity(UserDto2 userDto) {
        if (userDto == null) {
            return null;
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(userDto.getUserId());
        userEntity.setName(userDto.getName());
        userEntity.setProfile(mapDtoToProfile(userDto.getProfile()));
        return userEntity;
    }

    @Named("mapProfileToDto")
    default ProfileDto mapProfileToDto(Profile profile) {
        if (profile == null) {
            return null;
        }
        ProfileDto profileDto = new ProfileDto();
        profileDto.setProfileId(profile.getProfileId());
        profileDto.setUserEntity(profile.getUserEntity().getUserId()); // Assuming userEntity is a UserEntity
        profileDto.setBio(profile.getBio());
        profileDto.setProfilePictureUrl(profile.getProfilePictureUrl());
        profileDto.setCoverPictureUrl(profile.getCoverPictureUrl());
        profileDto.setCreatedAt(profile.getCreatedAt());
        profileDto.setMisc(profile.getMisc());
        profileDto.setLastUpdated(profile.getLastUpdated());
        return profileDto;
    }

    @Named("mapDtoToProfile")
    default Profile mapDtoToProfile(ProfileDto profileDto) {
        if (profileDto == null) {
            return null;
        }
        Profile profile = new Profile();
        profile.setProfileId(profileDto.getProfileId());
        // Assuming the user is already set in the correct context, otherwise set it manually.
        // profile.setUserEntity(...);
        profile.setBio(profileDto.getBio());
        profile.setProfilePictureUrl(profileDto.getProfilePictureUrl());
        profile.setCoverPictureUrl(profileDto.getCoverPictureUrl());
        profile.setCreatedAt(profileDto.getCreatedAt());
        profile.setMisc(profileDto.getMisc());
        profile.setLastUpdated(profileDto.getLastUpdated());
        return profile;
    }
}
