package com.example.card_user.service.validation;

import com.example.card_user.dto.CardDto;
import com.example.card_user.dto.ErrorDto;
import com.example.card_user.test.CardTest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CardValidation {
    public List<ErrorDto> validation(CardDto dto){
        List<ErrorDto> errors = new ArrayList<>();
        if (dto.getCardNumber() != null && !CardTest.isNumber(dto.getCardNumber())){
            errors.add(new ErrorDto(dto.getCardNumber(),"Card number must consist of Numbers Only."));
        }

        if (dto.getCardPassword() != null && !CardTest.isNumber(dto.getCardPassword())){
            errors.add(new ErrorDto(dto.getCardPassword(),"Password must consist of Numbers Only."));
        }

      return errors;

    }




}
