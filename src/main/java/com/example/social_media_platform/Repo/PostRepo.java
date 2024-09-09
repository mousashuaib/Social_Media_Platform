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

    @Query("SELECT p FROM Post p WHERE p.userEntity IN (SELECT f.userEntity2 FROM Friendship f WHERE f.userEntity1 = :userEntity) " +
            "OR p.userEntity IN (SELECT f.userEntity1 FROM Friendship f WHERE f.userEntity2 = :userEntity)")
    List<Post> findPostsByFriends(UserEntity userEntity);


}
