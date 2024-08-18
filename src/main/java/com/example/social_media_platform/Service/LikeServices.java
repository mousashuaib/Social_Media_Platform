package com.example.social_media_platform.Service;

import com.example.social_media_platform.Model.Dto.LikeDto;
import com.example.social_media_platform.Model.Entity.Comment;
import com.example.social_media_platform.Model.Entity.Like;
import com.example.social_media_platform.Model.Entity.Post;
import com.example.social_media_platform.Model.Entity.UserEntity;
import com.example.social_media_platform.Model.Mapper.LikeMapper;
import com.example.social_media_platform.Repo.CommentRepo;
import com.example.social_media_platform.Repo.LikeRepo;
import com.example.social_media_platform.Repo.PostRepo;
import com.example.social_media_platform.Repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class LikeServices {
    @Autowired
    private LikeRepo likeRepo;
    @Autowired
    private LikeMapper likeMapper;
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private CommentRepo commentRepo;

    @Transactional
    public LikeDto likePost(Long postId, Long userId) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        UserEntity userEntity = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        Like like = new Like();
        like.setPost(post);
        like.setUserEntity(userEntity);

        Like savedLike = likeRepo.save(like);
        return likeMapper.toDto(savedLike);
    }

    @Transactional
    public void unlikePost(Long postId, Long userId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        UserEntity user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Like like = likeRepo.findByPostAndUserEntity(post, user)
                .orElseThrow(() -> new RuntimeException("Like not found"));

        likeRepo.delete(like);

    }

    @Transactional
    public LikeDto likeComment(Long commentId, Long userId) {
        Comment comment = commentRepo.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        UserEntity user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Optional<Like> existingLike = likeRepo.findByCommentAndUserEntity(comment, user);
        if (existingLike.isPresent()) {
            System.out.println("Comment already liked.");
        }
        Like like = new Like();
        like.setComment(comment);
        like.setUserEntity(user);

        Like savedLike = likeRepo.save(like);
        return likeMapper.toDto(savedLike);

    }


    @Transactional
    public String unlikeComment(Long commentId, Long userId) {
        Comment comment = commentRepo.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        UserEntity user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Like like = likeRepo.findByCommentAndUserEntity(comment, user)
                .orElseThrow(() -> new RuntimeException("Like not found"));

        likeRepo.delete(like);

        return "Comment successfully unliked.";
    }

}


