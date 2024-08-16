package com.example.social_media_platform.Model.Entity;

import lombok.Data;
import jakarta.persistence.*;


@Data
@Entity
@Table(name = "media")
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mediaId;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(name = "media_type", length = 50)
    private String mediaType;

    @Column(name = "media_url", length = 255)
    private String mediaUrl;

}
