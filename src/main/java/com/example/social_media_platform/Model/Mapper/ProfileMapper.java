package com.example.social_media_platform.Model.Mapper;

import com.example.social_media_platform.Model.Dto.ProfileDto;
import com.example.social_media_platform.Model.Entity.Profile;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    ObjectMapper objectMapper = new ObjectMapper();

    @Mapping(source = "userEntity.userId", target = "userEntity")
    ProfileDto toDto(Profile profile);

    @Mapping(source = "userEntity", target = "userEntity.userId")
    Profile toEntity(ProfileDto profileDto);


}
