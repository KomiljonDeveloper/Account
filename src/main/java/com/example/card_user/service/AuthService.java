package com.example.card_user.service;

import com.example.card_user.dto.*;
import com.example.card_user.model.Auth;
import com.example.card_user.model.AuthAccessSession;
import com.example.card_user.model.AuthRefreshSession;
import com.example.card_user.repository.AuthAccessSessionRepository;
import com.example.card_user.repository.AuthRefreshSessionRepository;
import com.example.card_user.repository.AuthRepository;
import com.example.card_user.service.mapper.AuthMapper;
import com.example.card_user.security.JwtUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
    private final JwtUtils jwtUtils;
    private final AuthMapper authMapper;
    private final AuthRepository authRepository;
    private final AuthRefreshSessionRepository authRefreshSessionRepository;
    private final AuthAccessSessionRepository authAccessSessionRepository;

    public ResponseDto<AuthDto> register(AuthDto dto) {
        try {
            dto.setCreatedAt(LocalDateTime.now());
            return ResponseDto.<AuthDto>builder()
                    .message("OK")
                    .success(true)
                    .date(this.authMapper.toDto(
                            this.authRepository.save(
                                    this.authMapper.toEntity(dto)
                            )))
                    .build();
        } catch (Exception e) {
            return ResponseDto.<AuthDto>builder()
                    .message(String.format("Error message :%s", e.getMessage()))
                    .code(-2)
                    .build();
        }

    }

    public ResponseDto<TokenResponseDto> registerConfirm(RegisterConfirmDto dto) {
        return this.authRepository.findByUsernameAndDeletedAtIsNull(dto.getUsername())
                .map(auth -> {
                    auth.setEnable(true);
                    Auth save = this.authRepository.save(auth);
                    String token = toJsonByAuth(save);

                    this.authAccessSessionRepository.save(new AuthAccessSession(token,this.authMapper.toDto(save)));
                    this.authRefreshSessionRepository.save(new AuthRefreshSession(token,this.authMapper.toDto(save)));

                    return ResponseDto.<TokenResponseDto>builder()
                            .message("Ok")
                            .date(TokenResponseDto.builder()
                                    .accessToken(this.jwtUtils.generateToken(token))
                                    .refreshToken(this.jwtUtils.generateToken(token))
                                    .build())
                            .build();
                })
                .orElse(
                        ResponseDto.<TokenResponseDto>builder()
                                .message("Username is not found!")
                                .code(-1)
                                .build());

    }
@Transactional
    public ResponseDto<TokenResponseDto> logIn(LoginDto dto) {
        return this.authRepository.findByUsernameAndEnableIsTrueAndDeletedAtIsNull(dto.getUsername())
                .map(auth -> {
                    String token = toJsonByAuth(auth);

                    this.authAccessSessionRepository.save(new AuthAccessSession(token,this.authMapper.toDto(auth)));
                    this.authRefreshSessionRepository.save(new AuthRefreshSession(token,this.authMapper.toDto(auth)));


                    return ResponseDto.<TokenResponseDto>builder()
                            .message("OK")
                            .success(true)
                            .date(TokenResponseDto.builder()
                                    .accessToken(this.jwtUtils.generateToken(token))
                                    .refreshToken(this.jwtUtils.generateToken(token))
                                    .build())
                            .build();
                })
                .orElse(
                        ResponseDto.<TokenResponseDto>builder()
                                .message("Username is not valid!")
                                .code(-1)
                                .build());

    }

    public ResponseDto<TokenResponseDto> refreshToken(String token) {
        return null;
    }

/*
    public ResponseDto<AuthDto> getAuth(Integer id) {
        return this.authRepository.findByAuthIdAndDeletedAtIsNull(id).map(auth ->
            ResponseDto.<AuthDto>builder()
                    .message("Ok")
                    .success(true)
                    .date(this.authMapper.toDto(auth))
                    .build()
        ).orElse(
                ResponseDto.<AuthDto>builder()
                .message("Auth is not found!")
                .code(-1)
                .build());

    }*/

    @Override
    public AuthDto loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.authRepository.findByUsernameAndEnableIsTrueAndDeletedAtIsNull(username)
                .map(this.authMapper::toDtoWithAuth)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("This %s username is not found!", username)));
    }


    private String toJsonByAuth(Auth dto) {
        return "AuthDto{" +
                "authId-" + dto.getAuthId() +
                ", first_name-'" + dto.getFirst_name() + '\'' +
                ", last_name-'" + dto.getLast_name() + '\'' +
                ", username-'" + dto.getUsername() + '\'' +
                ", enable-" + dto.getEnable() +
                '}';
    }
}
