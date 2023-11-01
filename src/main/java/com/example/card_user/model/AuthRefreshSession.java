package com.example.card_user.model;

import com.example.card_user.dto.AuthDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RedisHash(timeToLive =3600_000 * 24 * 7)
public class AuthRefreshSession {
    @Id
    private String sessionId;
    private AuthDto authDto;

}
