package com.example.social_media_platform.Repo;

import com.example.social_media_platform.Model.Dto.ProfileDto;
import com.example.social_media_platform.Model.Entity.Profile;
import com.example.social_media_platform.Model.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByUserEntity_UserId(Long userId);

    Optional<Profile> findByUserEntity(UserEntity user);
}
