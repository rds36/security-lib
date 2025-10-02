package com.rds.securitylib.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.MacAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

public class JwtService {
    private final String issuer;
    private final int expiration;
    private final MacAlgorithm algorithm;
    private final SecretKey secretKey;

    public static JwtService build(String issuer, int expiration, String base64SecretKey) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(base64SecretKey));
        return new JwtService(issuer, expiration, Jwts.SIG.HS256, key);
    }

    private JwtService(String issuer, int expiration, MacAlgorithm algorithm, SecretKey secretKey) {
        this.issuer = issuer;
        this.expiration = expiration;
        this.algorithm = algorithm;
        this.secretKey = secretKey;
    }

    public String generate(String subject, Map<String, Object> claims){
        Instant now = Instant.now();

        // create JWT
        JwtBuilder builder = Jwts.builder()
                .issuer(issuer)
                .subject(subject)
                .claims(claims)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(expiration)));

        // sign JWT
        builder = builder.signWith(secretKey, algorithm);
        return builder.compact();
    }

    public Jws<Claims> parse(String token) {
        JwtParserBuilder parserBuilder = Jwts.parser().requireIssuer(issuer);
        parserBuilder = parserBuilder.verifyWith(secretKey);

        return parserBuilder.build().parseSignedClaims(token);
    }
}
