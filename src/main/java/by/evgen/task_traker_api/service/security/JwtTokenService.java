package by.evgen.task_traker_api.service.security;

import by.evgen.task_traker_api.dto.security.UserResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtTokenService {
    @Value("${security.jwt.access_token.secret_key}")
    private String jwtSecretKey;

    @Value("${security.jwt.access_token.expiration}")
    private Duration accessTokenExpiration;

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    //здесь осуществляется извлечение утверждений
    //также проверяется подлинность токена
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    public String extractUsername(String accessToken) {
        return extractClaim(accessToken, Claims::getSubject);
    }

    public Instant extractExpiration(String accessToken) {
        return extractClaim(accessToken, claims ->
                claims.getExpiration().toInstant());
    }

    public Long extractUserId(String token) {
        return extractClaim(token, claims -> claims.get("id", Long.class));
    }

    public String generateAccessToken(UserResponse user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.id());
        claims.put("role", user.roles());

        return Jwts.builder()
                .claims(claims)
                .subject(user.username())
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plus(accessTokenExpiration)))
                .signWith(getSigningKey(), Jwts.SIG.HS256)
                .compact();
    }

    public boolean isValidToken(String token, String username) {

        String extractedUsername = extractUsername(token);

        Instant expiration = extractExpiration(token);

        return extractedUsername.equals(username)
                && expiration.isAfter(Instant.now());
    }
}

