package com.example.card_user.security;

import com.example.card_user.dto.AuthDto;
import com.example.card_user.service.AuthService;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;
@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final AuthService authService;
    private  final PasswordEncoder passwordEncoder;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");
        if (!StringUtils.isBlank(authorization) && authorization.startsWith("Basic ")){
            String auth = authorization.substring(6);
            String s = new String(Base64.getDecoder().decode(auth));
            String username = s.split(":")[0];
            String password = s.split(":")[1];

            AuthDto authDto = this.authService.loadUserByUsername(username);
            if (passwordEncoder.matches(password,authDto.getPassword())) {

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                authDto,
                                authDto.getPassword(),
                                authDto.getAuthorities()
                        );
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            }
           filterChain.doFilter(request,response);
        }


    }
}
