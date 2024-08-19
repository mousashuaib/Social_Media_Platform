package com.example.social_media_platform.Model.Entity;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "media")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "mediaId")
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
