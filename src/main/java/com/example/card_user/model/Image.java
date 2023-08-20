package com.example.card_user.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Image {
    @Id
    @SequenceGenerator(name = "file_seq",sequenceName = "file_sequence",allocationSize = 1)
    @GeneratedValue(generator = "file_seq")
    private Integer imageId;
    private String imageName;
    private String path;
    private String ext;
    @Column(name = "user_id",unique = true)
    private Integer userId;
    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",insertable = false,updatable = false)
    private User user;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
