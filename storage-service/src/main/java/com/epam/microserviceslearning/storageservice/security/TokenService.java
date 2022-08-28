package com.epam.microserviceslearning.storageservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class TokenService {
    private static final String JWT_SECRET_KEY_PROPERTY = "service.auth.jwt.secret-key";
    private final Key secretKey;

    @Value("${service.auth.jwt.validity-time}")
    private int jwtValidityTime; // in days

    public TokenService(Environment environment) {
        final String rawKey = Objects.requireNonNull(environment.getProperty(JWT_SECRET_KEY_PROPERTY));
        secretKey = Keys.hmacShaKeyFor(rawKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateJwt(Map<String, Object> claims) {
        final Instant expirationInstant = LocalDateTime.now(Clock.systemUTC())
                .plus(jwtValidityTime, ChronoUnit.DAYS)
                .toInstant(ZoneOffset.UTC);
        final Date expirationTime = Date.from(expirationInstant);

        final JwtBuilder builder = Jwts.builder()
                .setExpiration(expirationTime)
                .signWith(secretKey);
        claims.forEach(builder::claim);

        return builder.compact();
    }

    public Map<String, Object> parseToken(String token) {
        try {
            final Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return new HashMap<>(claims);
        } catch (JwtException e) {
            return Collections.emptyMap();
        }
    }
}
