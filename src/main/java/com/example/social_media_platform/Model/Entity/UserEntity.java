package com.example.social_media_platform.Model.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user_entity")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userId")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true, length = 255)
    private String name;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @OneToOne(mappedBy = "userEntity", cascade = CascadeType.ALL)
    private Profile profile;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    private Set<Post> posts;

    @JsonManagedReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Comment> comments;

    @JsonManagedReference
    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
    private Set<FriendRequest> sentRequests;

    @JsonManagedReference
    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
    private Set<FriendRequest> receivedRequests;

    @OneToMany(mappedBy = "userEntity1", cascade = CascadeType.ALL)
    private Set<Friendship> friendships;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public UserEntity(String name, String email, String password) {
        this.email = email;
        this.password = password;
        this.name = name;
    }
}
