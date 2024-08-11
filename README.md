# Social Media Platform

## Overview

This project is a social media platform that will be built using Spring Boot. The application will allow users to create and manage posts, comments, likes, and friendships. The platform supports essential social media features, including user interactions, content management, and relationship tracking.

## Features

- **User Management**: Users can create accounts, manage their profiles, and authenticate securely.
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


## Quick Insight about the database Design

![image](https://github.com/user-attachments/assets/f47e1927-49b6-4d76-9d3d-9dec71627413)


