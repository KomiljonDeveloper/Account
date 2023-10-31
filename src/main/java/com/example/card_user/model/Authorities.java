package com.example.card_user.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "authorities")
public class Authorities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer authoritiesId;
    private String username;
    private String authority;
    private Integer userId;

    @ManyToOne
    @JoinTable(
            name = "auth_authorities",
            joinColumns = @JoinColumn(name = "authorities_id"),
            inverseJoinColumns = @JoinColumn(name = "auth_id")
    )
    private Auth auth;
}
