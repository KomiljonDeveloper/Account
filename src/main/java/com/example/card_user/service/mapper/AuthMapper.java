package com.example.card_user.service.mapper;

import com.example.card_user.dto.AuthDto;
import com.example.card_user.model.Auth;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthMapper {
    private final PasswordEncoder passwordEncoder;

    public Auth toEntity(AuthDto dto) {
        return Auth.builder()
                .password(passwordEncoder.encode(dto.getPassword()))
                .username(dto.getUsername())
                .createdAt(dto.getCreatedAt())
                .deletedAt(dto.getDeletedAt())
                .updatedAt(dto.getUpdatedAt())
                .enable(true)
                .build();
    }

    public AuthDto toDto(Auth entity) {
        return AuthDto.builder()
                .authId(entity.getAuthId())
                .createdAt(entity.getCreatedAt())
                .deletedAt(entity.getDeletedAt())
                .updatedAt(entity.getUpdatedAt())
                .enable(entity.getEnable())
                .password(entity.getPassword())
                .username(entity.getUsername())
                .build();
    }
public AuthDto toDtoWithAuth(Auth entity) {
        return AuthDto.builder()
                .authId(entity.getAuthId())
                .authorities(entity.getAuthorities())
                .createdAt(entity.getCreatedAt())
                .deletedAt(entity.getDeletedAt())
                .updatedAt(entity.getUpdatedAt())
                .enable(entity.getEnable())
                .password(entity.getPassword())
                .username(entity.getUsername())
                .build();
    }


}
