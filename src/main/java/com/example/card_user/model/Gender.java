package com.example.card_user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Gender {
    Male("Male"),
    Female("Female");
    private final String title;
}
