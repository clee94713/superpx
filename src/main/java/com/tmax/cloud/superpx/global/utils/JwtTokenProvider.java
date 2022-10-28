package com.tmax.cloud.superpx.global.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {
    @Getter
    public static class SampleClaim {
        String id;
        String name;

        SampleClaim() {
            id = "id";
            name = "name";
        }
    }

    private final String JWT_SECRET = "jwt-secret-key-sample";
    private static final long JWT_EXPIRATION_MS = 1000L * 60 * 60;


    public String generateToken(SampleClaim sampleClaim) {
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject("sampleToken")
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_MS))
                .claim("sampleClaim", sampleClaim)
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET.getBytes())
                .compact();
    }

    public SampleClaim getClaimFromJwtToken(String token) {
        ObjectMapper mapper = new ObjectMapper();

        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();

        return mapper.convertValue(claims.get("sampleClaim"), SampleClaim.class);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }
}