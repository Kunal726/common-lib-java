package com.projects.marketmosaic.common.utils;

import com.projects.marketmosaic.common.config.ZooKeeperConfig;
import com.projects.marketmosaic.common.exception.MarketMosaicCommonException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor

public class JWTUtils {
    private final String secret;

    private SecretKey key;

    @Autowired
    public JWTUtils(final ZooKeeperConfig zooKeeperConfig) {
        this.secret = zooKeeperConfig.getStringValueByKey("JWT_SECRET");
    }

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }


    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            log.error("Error parsing JWT token: {}", e.getMessage());
            throw new MarketMosaicCommonException("Unable to Parse jwt", 401);
        }
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


    public Long extractUserId(String token) {
        return extractClaim(token, claims -> claims.get("userId", Long.class));
    }

    public List<String> extractRole(String token) {
        List<?> rawList = extractClaim(token, claims -> claims.get("roles", List.class));

        if (rawList == null) {
            return List.of(); // empty immutable list
        }

        return rawList.stream()
                .filter(Objects::nonNull)
                .map(Object::toString)
                .toList();
    }
}
