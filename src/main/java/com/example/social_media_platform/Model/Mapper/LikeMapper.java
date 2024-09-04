package com.example.social_media_platform.Model.Mapper;

import com.example.social_media_platform.Model.Dto.LikeDto;
import com.example.social_media_platform.Model.Dto.ProfileDto;
import com.example.social_media_platform.Model.Dto.UserDto2;
import com.example.social_media_platform.Model.Entity.Like;
import com.example.social_media_platform.Model.Entity.Post;
import com.example.social_media_platform.Model.Entity.Comment;
import com.example.social_media_platform.Model.Entity.UserEntity;
import com.example.social_media_platform.Model.Entity.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface LikeMapper {

    @Mapping(source = "userEntity", target = "userEntity", qualifiedByName = "mapUserEntityToDto")
    @Mapping(source = "post", target = "post", qualifiedByName = "mapPostToLong")
    @Mapping(source = "comment", target = "comment", qualifiedByName = "mapCommentToLong")
    LikeDto toDto(Like like);

    @Mapping(source = "userEntity", target = "userEntity", qualifiedByName = "mapUserDtoToEntity")
    @Mapping(source = "post", target = "post", qualifiedByName = "mapLongToPost")
    @Mapping(source = "comment", target = "comment", qualifiedByName = "mapLongToComment")
    Like toEntity(LikeDto likeDto);

    @Named("mapUserEntityToDto")
    default UserDto2 mapUserEntityToDto(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }
        UserDto2 userDto = new UserDto2();
        userDto.setUserId(userEntity.getUserId());
        userDto.setName(userEntity.getName());
        userDto.setProfile(mapProfileToDto(userEntity.getProfile())); // Map Profile to ProfileDto
        return userDto;
    }

    @Named("mapUserDtoToEntity")
    default UserEntity mapUserDtoToEntity(UserDto2 userDto) {
        if (userDto == null) {
            return null;
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(userDto.getUserId());
        userEntity.setName(userDto.getName());
        userEntity.setProfile(mapDtoToProfile(userDto.getProfile())); // Map ProfileDto to Profile
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

    @Named("mapPostToLong")
    default Long mapPostToLong(Post post) {
        return post != null ? post.getPostId() : null;
    }

    @Named("mapCommentToLong")
    default Long mapCommentToLong(Comment comment) {
        return comment != null ? comment.getComment_id() : null;
    }

    @Named("mapLongToPost")
    default Post mapLongToPost(Long postId) {
        if (postId == null) {
            return null;
        }
        Post post = new Post();
        post.setPostId(postId);
        return post;
    }

    @Named("mapLongToComment")
    default Comment mapLongToComment(Long commentId) {
        if (commentId == null) {
            return null;
        }
        Comment comment = new Comment();
        comment.setComment_id(commentId);
        return comment;
    }
}
