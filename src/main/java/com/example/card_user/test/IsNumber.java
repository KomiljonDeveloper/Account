package com.example.card_user.test;

public class IsNumber {
    public boolean isNumber(String number){
        for (int i = 0; i < number.length(); i++) {
            if (number.charAt(i)<48 && number.charAt(i)>57){
                return false;
            }
        }
        return true;
    }

}
