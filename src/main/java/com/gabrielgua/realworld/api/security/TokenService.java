package com.gabrielgua.realworld.api.security;

import com.gabrielgua.realworld.api.security.AuthProperties;
import com.gabrielgua.realworld.domain.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final AuthProperties properties;

    public String generateToken(String subject) {
        return buildToken(new HashMap<String, Object>(), subject);
    }

    public String generateToken(Map<String, Object> extraClaims, String subject) {
        return buildToken(extraClaims, subject);
    }

    private String buildToken(Map<String, Object> extraClaims, String subject) {
        var nowMillis = System.currentTimeMillis();

        return Jwts
                .builder()
                .setSubject(subject)
                .setClaims(extraClaims)
                .setIssuer("gabrielgua-realworld")
                .setIssuedAt(new Date(nowMillis))
                .setExpiration(new Date(nowMillis + properties.getToken().getExpiration()))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, String subject) {
        final String email = extractEmail(token);
        return email.equals(subject) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsTFunction) {
        final Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getKey() {
        byte[] bytes = Decoders.BASE64.decode(properties.getToken().getSecret());
        return Keys.hmacShaKeyFor(bytes);
    }

}
