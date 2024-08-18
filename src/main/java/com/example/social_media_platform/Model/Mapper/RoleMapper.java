package com.example.social_media_platform.Model.Mapper;


import com.example.social_media_platform.Model.Dto.RoleDto;
import com.example.social_media_platform.Model.Entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class})

public interface RoleMapper {

        @Mapping(target = "users", source = "users")
        Role toEntity(RoleDto roleDto);

        @Mapping(source = "users", target = "users")
        RoleDto toDto(Role role);
}