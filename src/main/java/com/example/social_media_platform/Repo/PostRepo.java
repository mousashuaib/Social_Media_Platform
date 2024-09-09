package com.example.social_media_platform.Repo;

import com.example.social_media_platform.Model.Entity.Post;
import com.example.social_media_platform.Model.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepo extends JpaRepository<Post, Long> {

    @Override
    Optional<Post> findById(Long aLong);

    List<Post> findByUserEntity(UserEntity userEntity);


}
