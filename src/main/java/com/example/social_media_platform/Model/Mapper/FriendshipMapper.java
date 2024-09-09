package com.example.social_media_platform.Model.Mapper;

import com.example.social_media_platform.Model.Dto.FriendshipDto;

import com.example.social_media_platform.Model.Dto.ProfileDto;
import com.example.social_media_platform.Model.Dto.UserDto2;
import com.example.social_media_platform.Model.Entity.Friendship;
import com.example.social_media_platform.Model.Entity.Profile;
import com.example.social_media_platform.Model.Entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FriendshipMapper {

    @Mapping(source = "userEntity1", target = "userEntity1", qualifiedByName = "mapUserEntityToDto")
    @Mapping(source = "userEntity2", target = "userEntity2", qualifiedByName = "mapUserEntityToDto")
    FriendshipDto toDto(Friendship friendship);

    @Mapping(source = "userEntity1", target = "userEntity1", qualifiedByName = "mapUserDtoToEntity")
    @Mapping(source = "userEntity2", target = "userEntity2", qualifiedByName = "mapUserDtoToEntity")
    Friendship toEntity(FriendshipDto friendshipDto);

    List<Friendship> toDtoList(List<Friendship> friendships);

    List<FriendshipDto>toDtoList2(List<Friendship> friendships);


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
        profile.setBio(profileDto.getBio());
        profile.setProfilePictureUrl(profileDto.getProfilePictureUrl());
        profile.setCoverPictureUrl(profileDto.getCoverPictureUrl());
        profile.setCreatedAt(profileDto.getCreatedAt());
        profile.setMisc(profileDto.getMisc());
        profile.setLastUpdated(profileDto.getLastUpdated());
        return profile;
    }
}
