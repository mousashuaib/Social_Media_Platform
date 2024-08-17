package com.example.social_media_platform.Model.Entity;

import jakarta.persistence.*;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; // e.g., ROLE_USER, ROLE_ADMIN

    @ManyToMany(mappedBy = "roles")
    private Set<UserEntity> users;


}
