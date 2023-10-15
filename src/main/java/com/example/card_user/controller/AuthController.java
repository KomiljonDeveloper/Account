package com.example.card_user.controller;

import com.example.card_user.dto.AuthDto;
import com.example.card_user.dto.ResponseDto;
import com.example.card_user.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping
    public ResponseDto<AuthDto> create(@RequestBody AuthDto dto){
        return this.authService.createAuth(dto);
    }
    @GetMapping
    public ResponseDto<AuthDto> get(@RequestParam Integer id){
        return this.authService.getAuth(id);
    }

}
