package com.example.social_media_platform.Model.Entitiy;

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

    @Column(name = "media_type", nullable = false, length = 50)
    private String mediaType;

    @Column(name = "media_url", nullable = false, length = 255)
    private String mediaUrl;

}
