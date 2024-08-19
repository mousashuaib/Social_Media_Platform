package com.example.social_media_platform.Model.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.sql.Timestamp;


@Getter
@Setter
@Entity
@Table(name = "friendship", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id1", "user_id2"})
})
public class Friendship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long friendshipId;


    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id1", nullable = false)
    private UserEntity userEntity1;


    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id2", nullable = false)
    private UserEntity userEntity2;

    @Column(nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp timestamp;

//    @Column(name = "last_updated", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
//    private Timestamp lastUpdated;




    //updated at is not provided in postgries, the implementaion is using trigers
    @Column(name = "last_updated")
    private Timestamp lastUpdated;


}
