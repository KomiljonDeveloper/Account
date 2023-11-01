package com.example.card_user.security;

import com.example.card_user.model.AuthAccessSession;
import com.example.card_user.repository.AuthAccessSessionRepository;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtSecurityFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final AuthAccessSessionRepository authAccessSessionRepository;
    @Override
    protected void doFilterInternal(
           @NonNull HttpServletRequest request,
           @NonNull HttpServletResponse response,
           @NonNull FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");

        if (!StringUtils.isEmpty(authorization) && authorization.startsWith("Bearer ")){
            String token = authorization.substring(7);
            if (this.jwtUtils.isValid(token)){
                this.authAccessSessionRepository.findById(
                        this.jwtUtils.getClaims(token, "sub", String.class))
                        .ifPresent(accessSession -> {
                            SecurityContextHolder.getContext().setAuthentication(
                                    new UsernamePasswordAuthenticationToken(
                                            accessSession.getAuthDto(),
                                            accessSession.getAuthDto().getPassword(),
                                            accessSession.getAuthDto().getAuthorities()
                                    ));

                        });


            }

        }
          filterChain.doFilter(request,response);

    }


}
