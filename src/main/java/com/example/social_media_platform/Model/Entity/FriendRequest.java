package com.example.social_media_platform.Model.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "friend_request")
public class FriendRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;


    //infinite loop for sender and receiver because of bi-directional relationship
    //jscson goes into infinte loop and throws stackoverflow error
    //the reasone that friend request entity has two fields sender and receiver from user entity type
    //and user entity has a field of friend request type
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private UserEntity sender;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private UserEntity receiver;

    @Enumerated(EnumType.STRING)
    @Column(name ="status", nullable = false, length = 50)
    private FriendRequestStatus status;

    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;

    // Convenience method for updating status
    public void acceptRequest() {
        this.status = FriendRequestStatus.ACCEPTED;
    }

    public void rejectRequest() {
        this.status = FriendRequestStatus.REJECTED;
    }

    public void markAsPending() {
        this.status = FriendRequestStatus.PENDING;
    }
}

