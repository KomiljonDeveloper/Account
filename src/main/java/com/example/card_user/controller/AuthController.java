package com.example.card_user.controller;

import com.example.card_user.dto.*;
import com.example.card_user.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("register/")
    public ResponseDto<AuthDto> register(@RequestBody AuthDto dto){
        return this.authService.register(dto);
    }

    @PostMapping(value = "register-confirm/")
    public ResponseDto<TokenResponseDto> registerConfirm(@RequestBody RegisterConfirmDto dto){
        return this.authService.registerConfirm(dto);
    }
    @PostMapping(value = "login/")
    public ResponseDto<TokenResponseDto> logIn(@RequestBody LoginDto dto){
        return this.authService.logIn(dto);
    }

    @PostMapping(value = "refreshToken/")
    public ResponseDto<TokenResponseDto> refreshToken(@RequestParam(value = "token") String token){
        return this.authService.refreshToken(token);
    }

   /* @GetMapping
    public ResponseDto<AuthDto> get(@RequestParam Integer id){
        return this.authService.getAuth(id);
    }
*/
}
