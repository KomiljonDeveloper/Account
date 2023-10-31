package com.example.card_user.service;

import com.example.card_user.dto.AuthDto;
import com.example.card_user.dto.ResponseDto;
import com.example.card_user.repository.AuthRepository;
import com.example.card_user.service.mapper.AuthMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final AuthMapper authMapper;
    private final AuthRepository authRepository;

    public ResponseDto<AuthDto> createAuth(AuthDto dto) {
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

    }

    @Override
    public AuthDto loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.authRepository.findByUsernameAndEnableIsTrueAndDeletedAtIsNull(username)
                .map(this.authMapper::toDtoWithAuth)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("This %s username is not found!",username)));
    }
}
