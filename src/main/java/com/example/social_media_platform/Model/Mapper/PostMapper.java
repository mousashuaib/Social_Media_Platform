package com.example.social_media_platform.Model.Mapper;

import com.example.social_media_platform.Model.Dto.PostDto;
import com.example.social_media_platform.Model.Entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {MediaMapper.class, CommentMapper.class, LikeMapper.class})
public interface PostMapper {

    @Mapping(source = "userEntity.userId", target = "userEntity")
    PostDto toDto(Post post);

    @Mapping(source = "userEntity", target = "userEntity.userId")
    Post toEntity(PostDto postDto);
}
