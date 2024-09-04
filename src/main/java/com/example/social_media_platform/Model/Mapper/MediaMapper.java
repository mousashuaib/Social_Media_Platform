package com.example.social_media_platform.Model.Mapper;

import com.example.social_media_platform.Model.Dto.MediaDto;
import com.example.social_media_platform.Model.Entity.Media;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MediaMapper {

    // Mapping Media entity to MediaDto, mapping post.postId to post
    @Mapping(source = "post.postId", target = "post") // Ensure 'post' in MediaDto is the correct type
    MediaDto toDto(Media media);

    // Mapping MediaDto to Media entity, mapping post to post.postId
    @Mapping(source = "post", target = "post.postId")
    Media toEntity(MediaDto mediaDto);

    // List mapping method to map List<Media> to List<MediaDto>
    List<MediaDto> toDto(List<Media> mediaList);
}
