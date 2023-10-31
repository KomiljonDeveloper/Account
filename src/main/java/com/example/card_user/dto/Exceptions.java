package com.example.card_user.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    @ExceptionHandler
    public ResponseEntity<ResponseDto<Void>> usernameNotFoundException(UsernameNotFoundException e){
        return  new ResponseEntity<>(ResponseDto.<Void>builder()
                .message("Rejection value : "+e.getMessage())
                .code(-1)
                .build(), HttpStatus.NOT_FOUND);
    }

}
