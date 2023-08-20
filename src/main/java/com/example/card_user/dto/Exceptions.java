package com.example.card_user.dto;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class Exceptions {
    @ExceptionHandler
    public ResponseEntity<ResponseDto<Void>> methodArgumentException(MethodArgumentNotValidException e){
        return ResponseEntity.badRequest().body(ResponseDto.<Void>builder()
                        .message("Validation Error!")
                        .code(-4)
                        .errors(e.getFieldErrors().stream().map(fieldError -> {
                            String value = String.valueOf(fieldError.getRejectedValue());
                            String field = fieldError.getField();
                            String message = fieldError.getDefaultMessage();
                            return new ErrorDto(field, String.format("Message : %s , Rejection : %s", message, value));
                        }).toList())
                .build());
    }


}
