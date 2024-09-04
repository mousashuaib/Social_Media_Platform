package com.example.social_media_platform.Repo;
import com.example.social_media_platform.Model.Dto.UserDto;
import com.example.social_media_platform.Model.Entity.UserEntity;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<UserEntity> findByName(String username);


    List<UserEntity> findByNameContainingIgnoreCase(String name);

}

