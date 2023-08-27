package com.example.card_user.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.servlet.GenericServlet;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@Entity
@Table(name = "users",
        uniqueConstraints = {
        @UniqueConstraint(name = "email_unique_seq",columnNames = "email")},
        indexes = {
        @Index(name = "index_email",columnList = "email"),
        @Index(name = "index_id",columnList = "id")
        }
)
@NamedQueries(value = {
        @NamedQuery(name = "existsByEmail",
                query = "select case when count(u) > 0 " +
                        "then true else false end from User as u where u.email like :email and u.deletedAt is null"),
})

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String birthday;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;
    private LocalDateTime updatedAt;
}
