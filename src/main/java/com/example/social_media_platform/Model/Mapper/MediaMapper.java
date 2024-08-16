package com.example.social_media_platform.Model.Mapper;

import com.example.social_media_platform.Model.Dto.MediaDto;

import com.example.social_media_platform.Model.Entity.Media;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MediaMapper {

    @Mapping(source = "post.postId", target = "post")
    MediaDto toDto(Media media);

    @Mapping(source = "post", target = "post.postId")
    Media toEntity(MediaDto mediaDto);
}
