# Social Media Platform

## Overview

This project is a social media platform that will be built using Spring Boot. The application will allow users to create and manage posts, comments, likes, and friendships. The platform supports essential social media features, including user interactions, content management, and relationship tracking.

## Features

- **User Management**: Users can create accounts, manage their profiles, and authenticate securely
- **Post Creation**: Users can create and publish posts, including text content.
- **Commenting**: Users can comment on posts, enabling interactive discussions.
- **Liking**: Users can like posts and comments to express appreciation or approval.
- **Friendships**: Users can establish and manage friendships with other users.
- **Real-time Data**: Automatic timestamps are provided for posts, comments, likes, and friendships.

## Database Schema

The platform uses a relational database with the following schema:

### User Table

- **user_id**: Integer, Primary Key, Auto-Increment
- **name**: String, Not Null
- **email**: String, Unique, Not Null
- **password**: String, Not Null

### Profile Table

- **profile_id**: Integer, Primary Key, Auto-Increment
- **user_id**: Integer, Foreign Key (References `User(user_id)`)
- **profile_picture_url**: String, Nullable
- **created_at**: Timestamp, Default to Current Timestamp
- **updated_at**: Timestamp, Default to Current Timestamp on Update Current Timestamp

### FriendRequest Table

- **request_id**: Integer, Primary Key, Auto-Increment
- **sender_id**: Integer, Foreign Key (References `User(user_id)`)
- **receiver_id**: Integer, Foreign Key (References `User(user_id)`)
- **status**: String, Default to 'pending' -- ('pending', 'accepted', 'declined')
- **created_at**: Timestamp, Default to Current Timestamp

### Friendship Table

- **friendship_id**: Integer, Primary Key, Auto-Increment
- **user_id1**: Integer, Foreign Key (References `User(user_id)`)
- **user_id2**: Integer, Foreign Key (References `User(user_id)`)
- **timestamp**: Timestamp, Default to Current Timestamp
- **UNIQUE Constraint**: Ensures uniqueness of friendship pairs (`user_id1`, `user_id2`)

### Post Table

- **post_id**: Integer, Primary Key, Auto-Increment
- **user_id**: Integer, Foreign Key (References `User(user_id)`)
- **text**: Text, Not Null
- **date**: Timestamp, Default to Current Timestamp

### Media Table

- **media_id**: Integer, Primary Key, Auto-Increment
- **post_id**: Integer, Foreign Key (References `Post(post_id)`)
- **media_type**: String, Not Null -- ('image', 'video', etc.)
- **media_url**: String, Not Null

### Comment Table

- **comment_id**: Integer, Primary Key, Auto-Increment
- **user_id**: Integer, Foreign Key (References `User(user_id)`)
- **post_id**: Integer, Foreign Key (References `Post(post_id)`)
- **text**: Text, Not Null
- **date**: Timestamp, Default to Current Timestamp

### Likes Table

- **like_id**: Integer, Primary Key, Auto-Increment
- **user_id**: Integer, Foreign Key (References `User(user_id)`)
- **post_id**: Integer, Nullable, Foreign Key (References `Post(post_id)`)
- **comment_id**: Integer, Nullable, Foreign Key (References `Comment(comment_id)`)
- **date**: Timestamp, Default to Current Timestamp



## Endpoints

### User Controller

- **Update user info**: `PUT /v0/user/{userId}/update`
- **Get user name**: `GET /v0/user/{userId}/name`
- **Get user email**: `GET /v0/user/{userId}/email`
- **Get user suggestions**: `GET /v0/user/suggestions`
- **Find users by IDs**: `GET /v0/user/findByIds`
- **Find user by ID**: `GET /v0/user/findById/{id}`
- **Find user by email**: `GET /v0/user/findByEmail`
- **Search users**: `GET /v0/user/api/search/users`

### Profile Controller

- **Update profile picture**: `PUT /v0/profile/updateProfilePicture/{userId}`
- **Update profile info**: `PUT /v0/profile/updateInfo/{userId}`
- **Update cover picture**: `PUT /v0/profile/updateCoverPicture/{userId}`
- **Get profile by user ID**: `GET /v0/profile/{userId}`
- **Search profiles**: `GET /v0/profile/search`

### Post Controller

- **Update post**: `PUT /v0/post/update/{postId}`
- **Create new post**: `POST /v0/post/AddPost`
- **Get posts by user**: `GET /v0/post/postsByUser/{userId}`
- **Get post by ID**: `GET /v0/post/getPostById/{postId}`
- **Get all posts**: `GET /v0/post/getAll`
- **Get all posts (general)**: `GET /v0/post/getAllPosts`
- **Get friends' posts**: `GET /v0/post/friendsPosts`
- **Delete post**: `DELETE /v0/post/delete/{postId}`

### Media Controller

- **Update media**: `PUT /v0/media/updateMedia/{mediaId}`
- **Upload media**: `POST /v0/media/media`
- **Get media by ID**: `GET /v0/media/getById/{mediaId}`
- **Get all media**: `GET /v0/media/getAllMedia`
- **Delete media**: `DELETE /v0/media/deleteMedia/{mediaId}`

### Friend Request Controller

- **Reject friend request**: `PUT /v0/friend-request/reject/{requestId}`
- **Accept friend request**: `PUT /v0/friend-request/accept/{requestId}`
- **Send friend request**: `POST /v0/friend-request/send`
- **Get sent requests**: `GET /v0/friend-request/sent`
- **Get received requests**: `GET /v0/friend-request/received`
- **Cancel friend request**: `DELETE /v0/friend-request/cancel/{requestId}`

### Comment Controller

- **Get comment by ID**: `GET /v0/comments/{commentId}`
- **Update comment**: `PUT /v0/comments/{commentId}`
- **Delete comment**: `DELETE /v0/comments/{commentId}`
- **Add comment**: `POST /v0/comments/add`
- **Get all comments**: `GET /v0/comments/all`

### Like Controller

- **Like post**: `POST /v0/like/likePosts/{postId}`
- **Like comment**: `POST /v0/like/LikeComment/{commentId}`
- **Check if post is liked**: `GET /v0/like/isLiked/{postId}`
- **Unlike post**: `DELETE /v0/like/unlikePosts/{postId}`
- **Unlike comment**: `DELETE /v0/like/unlikeComment/{commentId}`

### Auth Controller

- **Register user**: `POST /v0/auth/register`
- **Login user**: `POST /v0/auth/login`
- **Get user by ID**: `GET /v0/auth/user/{userId}`

### Friendship Controller

- **Get user's friends**: `GET /v0/friendship/user/{userId}`


## Quick Insight about the database Design

![image](https://github.com/user-attachments/assets/f47e1927-49b6-4d76-9d3d-9dec71627413)


