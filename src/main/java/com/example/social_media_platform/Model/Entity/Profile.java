package com.example.social_media_platform.Model.Entity;



import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;


@Getter
@Setter
@Entity
@Table(name = "profile")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long profileId;



    /*
    here as we can see that the attribute name is userEntity as in the UserEntity class the mappedby attribute is userEntity
     */
    //FOREIGN KEY//
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

    //FOREIGN KEY//

    @Column(name = "bio", length = 255)
    private String bio;


    @Column(name = "profile_picture_url", length = 255)
    private String profilePictureUrl;

    @Column(name = "cover_picture_url", length = 255)
    private String coverPictureUrl;


    /*
      This means that the createdAt column in the friendRequest
      table will be of type TIMESTAMP and it will have a default value of the current timestamp.
     */
    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;

    @Column(name = "misc")
    private String misc;


    @Column(name = "last_updated")
    private Timestamp lastUpdated;


}

