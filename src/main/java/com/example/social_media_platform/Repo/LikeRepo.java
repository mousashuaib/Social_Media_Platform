package com.example.social_media_platform.Repo;

import com.example.social_media_platform.Model.Entity.Comment;
import com.example.social_media_platform.Model.Entity.Like;
import com.example.social_media_platform.Model.Entity.Post;
import com.example.social_media_platform.Model.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepo extends JpaRepository<Like, Long> {
    Optional<Like> findByPostAndUserEntity(Post post, UserEntity userEntity);
    Optional<Like> findByCommentAndUserEntity(Comment comment, UserEntity userEntity);

}
