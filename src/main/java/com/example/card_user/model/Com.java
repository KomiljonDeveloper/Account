package com.example.card_user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum Com {
    gmail("gmail"),
    mail("mail"),
    yahoo("yahoo");

    private String title;

}
