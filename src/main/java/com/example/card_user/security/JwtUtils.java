package com.example.card_user.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {
    @Value("${secret.key}")
    private String secretKey;

    public String generateToken(String user) {
        return Jwts.builder()
                .setSubject(user)
                .setExpiration(new Date(System.currentTimeMillis() + 3600_000 * 24 * 3))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .compact();
    }

    public boolean isValid(String token){
        if (!parser().isSigned(token)) return false;
        try {
            return !StringUtils.isBlank(parser()
                    .parseClaimsJws(token)
                    .getBody().getSubject());
        }catch (Exception e){
            return false;
        }
    }

    public <T> T getClaims(String token,String claimName,Class<T> type){
        try {
         return parser()
                 .parseClaimsJws(token)
                 .getBody()
                 .get(claimName,type);
    }catch (Exception e){
            return null;
        }

    }

    private JwtParser parser(){
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build();

    }
}
