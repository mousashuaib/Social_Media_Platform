package com.example.social_media_platform.Model.Entity;


import lombok.Data;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.Set;


@Data
@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long comment_id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String text;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp date;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    private Set<Like> likes;


}
