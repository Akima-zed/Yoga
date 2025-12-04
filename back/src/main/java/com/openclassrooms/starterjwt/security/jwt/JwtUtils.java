package com.openclassrooms.starterjwt.security.jwt;

import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class JwtUtils {
    @Value("${oc.app.jwtSecret}")
    private String jwtSecret;

    @Value("${oc.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateJwtToken(Authentication authentication) {

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .subject(userPrincipal.getUsername())
        .issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
        .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()), Jwts.SIG.HS512)
        .compact();
    }

    public String getUserNameFromJwtToken(String token) {
    return Jwts.parser()
            .verifyWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getSubject();
}


    public boolean validateJwtToken(String authToken) {
    try {
        Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                .build()
                .parseSignedClaims(authToken);
        return true;

    } catch (io.jsonwebtoken.security.SignatureException e) {
        log.error("Invalid JWT signature: {}", e.getMessage());
    } catch (MalformedJwtException e) {
        log.error("Invalid JWT token: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
        log.error("JWT token is expired: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
        log.error("JWT token is unsupported: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
        log.error("JWT claims string is empty: {}", e.getMessage());
    }
    return false;
}


    
}
