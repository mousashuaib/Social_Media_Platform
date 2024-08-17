package com.example.social_media_platform.Model.Entity;

import lombok.*;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "user_entity")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    /*
        cascade = CascadeType.ALL means that if you perform an operation on a UserEntity
        (like saving, deleting, or updating), the same operation will automatically be applied to the associated Profile entity.
        This simplifies database management by ensuring consistency.

        Common Cascade Types:

        CascadeType.ALL: Applies all types of operations (save, delete, etc.) to the related entity.
        CascadeType.PERSIST: Only cascades the save (insert) operation.
        CascadeType.MERGE: Cascades the update operation.
        CascadeType.REMOVE: Cascades the delete operation.
        CascadeType.REFRESH: Refreshes the state of the child entity when the parent is refreshed.
        CascadeType.DETACH: Detaches the child entity when the parent is detached.
     */

    /*
        mappedBy = "userEntity" specifies the field in the Profile entity that maps the relationship.
        In this case, the field is "userEntity" attribute in the Profile entity.
        This field should be the same as the field name in the Profile entity that maps the relationship.
     */
    @OneToOne(mappedBy = "userEntity", cascade = CascadeType.ALL)
    private Profile profile;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    private Set<Post> posts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Comment> comments;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
    private Set<FriendRequest> sentRequests;

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
    private Set<FriendRequest> receivedRequests;

    public UserEntity(String name, String email, String password) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();


}
