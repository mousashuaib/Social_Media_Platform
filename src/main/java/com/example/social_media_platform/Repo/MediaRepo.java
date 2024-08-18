package com.example.social_media_platform.Repo;

import com.example.social_media_platform.Model.Entity.Media;
import com.example.social_media_platform.Model.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaRepo extends JpaRepository<Media, Long> {
}
