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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Media media = (Media) o;

        return mediaId != null ? mediaId.equals(media.mediaId) : media.mediaId == null;
    }

    @Override
    public int hashCode() {
        return mediaId != null ? mediaId.hashCode() : 0;
    }

}
