package com.example.social_media_platform.Repo;
import com.example.social_media_platform.Model.Dto.UserDto;
import com.example.social_media_platform.Model.Entity.UserEntity;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<UserEntity> findByName(String username);


    List<UserEntity> findByNameContainingIgnoreCase(String name);


    // Custom query to find users who are not in the provided IDs (friends list or sent requests list)
    @Query("SELECT u FROM UserEntity u WHERE u.userId NOT IN :ids")
    List<UserEntity> findUsersNotInIds(@Param("ids") Set<Long> ids);

}

