package com.example.social_media_platform.Model.Mapper;

import com.example.social_media_platform.Model.Dto.CommentDto;
import com.example.social_media_platform.Model.Dto.PostDto;
import com.example.social_media_platform.Model.Dto.UserDto2;
import com.example.social_media_platform.Model.Entity.Comment;
import com.example.social_media_platform.Model.Entity.Post;
import com.example.social_media_platform.Model.Entity.UserEntity;
import com.example.social_media_platform.Model.Entity.Profile;
import com.example.social_media_platform.Model.Dto.ProfileDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {MediaMapper.class, CommentMapper.class, LikeMapper.class})
public interface PostMapper {

    // Mapping Post to PostDto
    @Mapping(source = "userEntity", target = "userEntity", qualifiedByName = "mapUserEntityToUserDto2")
    PostDto toDto(Post post);

    // Mapping PostDto to Post
    @Mapping(source = "userEntity", target = "userEntity", qualifiedByName = "mapUserDto2ToUserEntity")
    Post toEntity(PostDto postDto);

    @Named("mapUserEntityToUserDto2")
    default UserDto2 mapUserEntityToUserDto2(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }

        UserDto2 userDto2 = new UserDto2();
        userDto2.setUserId(userEntity.getUserId());
        userDto2.setName(userEntity.getName());
        userDto2.setProfile(mapProfileToDto(userEntity.getProfile())); // Map Profile to ProfileDto
        userDto2.setComments(userEntity.getComments().stream()
                .map(comment -> {
                    CommentDto commentDto = new CommentDto();
                    commentDto.setComment_id(comment.getComment_id());
                    commentDto.setText(comment.getText());
                    // Map additional fields as necessary
                    return commentDto;
                })
                .collect(Collectors.toSet()));
        return userDto2;
    }

    @Named("mapUserDto2ToUserEntity")
    default UserEntity mapUserDto2ToUserEntity(UserDto2 userDto2) {
        if (userDto2 == null) {
            return null;
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(userDto2.getUserId());
        userEntity.setName(userDto2.getName());
        userEntity.setProfile(mapDtoToProfile(userDto2.getProfile())); // Map ProfileDto to Profile

        // Handle comments
        if (userDto2.getComments() != null) {
            userEntity.setComments(userDto2.getComments().stream()
                    .map(commentDto -> {
                        Comment comment = new Comment();
                        comment.setComment_id(commentDto.getComment_id());
                        comment.setText(commentDto.getText());
                        // Add any additional fields as needed
                        return comment;
                    })
                    .collect(Collectors.toSet()));
        }

        return userEntity;
    }

    @Named("mapProfileToDto")
    default ProfileDto mapProfileToDto(Profile profile) {
        if (profile == null) {
            return null;
        }
        ProfileDto profileDto = new ProfileDto();
        profileDto.setProfileId(profile.getProfileId());
        profileDto.setUserEntity(profile.getUserEntity().getUserId());
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
