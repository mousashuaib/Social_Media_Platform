package com.example.social_media_platform.Model.Mapper;

import com.example.social_media_platform.Model.Dto.CommentDto;
import com.example.social_media_platform.Model.Dto.LikeDto;
import com.example.social_media_platform.Model.Entity.Comment;
import com.example.social_media_platform.Model.Entity.Like;
import com.example.social_media_platform.Model.Entity.UserEntity;
import com.example.social_media_platform.Model.Entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(source = "commentId", target = "commentId") // Make sure the names match
    @Mapping(source = "user.userId", target = "user")
    @Mapping(source = "post.postId", target = "post")
    @Mapping(source = "likes", target = "likes", qualifiedByName = "mapLikesToDto")
    CommentDto toDto(Comment comment);

    @Mapping(source = "commentId", target = "commentId") // Make sure the names match
    @Mapping(source = "user", target = "user", qualifiedByName = "mapLongToUserEntity")
    @Mapping(source = "post", target = "post", qualifiedByName = "mapLongToPost")
    @Mapping(source = "likes", target = "likes", qualifiedByName = "mapLikesToEntity")
    Comment toEntity(CommentDto commentDto);

    @Named("mapUserEntityToLong")
    default Long mapUserEntityToLong(UserEntity userEntity) {
        return userEntity != null ? userEntity.getUserId() : null;
    }

    @Named("mapPostToLong")
    default Long mapPostToLong(Post post) {
        return post != null ? post.getPostId() : null;
    }

    @Named("mapCommentToLong")
    default Long mapCommentToLong(Comment comment) {
        return comment != null ? comment.getCommentId() : null;
    }

    @Named("mapLongToUserEntity")
    default UserEntity mapLongToUserEntity(Long userId) {
        if (userId == null) {
            return null;
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(userId);
        return userEntity;
    }

    @Named("mapLongToPost")
    default Post mapLongToPost(Long postId) {
        if (postId == null) {
            return null;
        }
        Post post = new Post();
        post.setPostId(postId);
        return post;
    }

    @Named("mapLongToComment")
    default Comment mapLongToComment(Long commentId) {
        if (commentId == null) {
            return null;
        }
        Comment comment = new Comment();
        comment.setCommentId(commentId);
        return comment;
    }

    @Named("mapLikesToDto")
    default Set<LikeDto> mapLikesToDto(Set<Like> likes) {
        if (likes == null) {
            return null;
        }
        return likes.stream()
                .map(like -> LikeDto.builder()
                        .likeId(like.getLikeId())
                        .userEntity(mapUserEntityToLong(like.getUserEntity()))
                        .post(mapPostToLong(like.getPost()))
                        .comment(mapCommentToLong(like.getComment()))
                        .date(like.getDate())
                        .build())
                .collect(Collectors.toSet());
    }

    @Named("mapLikesToEntity")
    default Set<Like> mapLikesToEntity(Set<LikeDto> likeDtos) {
        if (likeDtos == null) {
            return null;
        }
        return likeDtos.stream()
                .map(likeDto -> {
                    Like like = new Like();
                    like.setLikeId(likeDto.getLikeId());
                    like.setUserEntity(mapLongToUserEntity(likeDto.getUserEntity()));
                    like.setPost(mapLongToPost(likeDto.getPost()));
                    like.setComment(mapLongToComment(likeDto.getComment()));
                    like.setDate(likeDto.getDate());
                    return like;
                })
                .collect(Collectors.toSet());
    }
}
