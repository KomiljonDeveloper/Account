package com.example.card_user.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@NamedQuery(name = "existsByCardNumber",
        query = "select case when count (c)>0 " +
                "then true else false end from Card as c where c.cardNumber = :cardNumber and c.deletedAt is null")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cardId;
    @Column(name = "user_id")
    private Integer userId;
    private String cardName;
    private String cardNumber;
    private String cardDate;
    private String cardPassword;
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",insertable = false,updatable = false)
    private User user;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;
    private LocalDateTime updatedAt;


}
