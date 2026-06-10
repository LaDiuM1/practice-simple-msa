package io.github.ladium1.study.practicesimplemsa.member.infrastructure.security;

import io.github.ladium1.study.practicesimplemsa.member.domain.exception.InvalidTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Configuration
public class JwtProvider {

    @Value("${jwt.private-key}")
    private String privateKeyBase64;
    @Value("${jwt.public-key}")
    private String publicKeyBase64;
    @Value("${jwt.access-expired-time}")
    private Duration accessExpiredTime;
    @Value("${jwt.refresh-expired-time}")
    private Duration refreshExpiredTime;

    private PrivateKey privateKey;
    private PublicKey publicKey;

    @PostConstruct
    void init() throws Exception {
        this.privateKey = loadPrivateKey();
        this.publicKey = loadPublicKey();
    }

    public String generateAccessToken(String memberId, String role) {
        return buildToken(memberId, Map.of("role", role), accessExpiredTime);
    }

    public String generateRefreshToken(String memberId) {
        return buildToken(memberId, Map.of(), refreshExpiredTime);
    }

    public Claims parseClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(publicKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidTokenException();
        }
    }

    private String buildToken(String subject, Map<String, ?> claims, Duration expiredTime) {
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(subject)
                .claims(claims)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(expiredTime)))
                .signWith(privateKey, Jwts.SIG.RS256)
                .compact();
    }

    private PublicKey loadPublicKey() throws Exception {
        byte[] der = Base64.getDecoder().decode(publicKeyBase64);
        return KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(der));
    }

    private PrivateKey loadPrivateKey() throws Exception {
        byte[] der = Base64.getDecoder().decode(privateKeyBase64);
        return KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(der));
    }

}
