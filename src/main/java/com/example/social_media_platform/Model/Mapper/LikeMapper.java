package com.example.social_media_platform.Model.Mapper;

import com.example.social_media_platform.Model.Dto.LikeDto;

import com.example.social_media_platform.Model.Entity.Like;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LikeMapper {

    @Mapping(source = "userEntity.userId", target = "userEntity")
    @Mapping(source = "post.postId", target = "post")
    @Mapping(source = "comment.comment_id", target = "comment")
    LikeDto toDto(Like like);

    @Mapping(source = "userEntity", target = "userEntity.userId")
    @Mapping(source = "post", target = "post.postId")
    @Mapping(source = "comment", target = "comment.comment_id")
    Like toEntity(LikeDto likeDto);
}
