package com.example.social_media_platform.Model.Entity;


import lombok.Data;
import jakarta.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Set;



@Data
@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String text;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp date;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private Set<Media> media;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private Set<Comment> comments;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private Set<Like> likes;

    @Column(name = "last_updated")
    private Timestamp lastUpdated;

    @PrePersist
    protected void onCreate() {
        this.date = Timestamp.from(Instant.now());
        this.lastUpdated = Timestamp.from(Instant.now());
    }

    @PreUpdate
    protected void onUpdate() {
        this.lastUpdated = Timestamp.from(Instant.now());
    }
    public void addMedia(Media media) {
        media.setPost(this);
        this.media.add(media);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Post post = (Post) o;

        return postId != null ? postId.equals(post.postId) : post.postId == null;
    }

    @Override
    public int hashCode() {
        return postId != null ? postId.hashCode() : 0;
    }
}



