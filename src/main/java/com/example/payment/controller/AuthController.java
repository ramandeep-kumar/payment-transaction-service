package com.talentica.paymentservice.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Value("${JWT_SECRET:change_me}")
    private String jwtSecret;

    @PostMapping("/token")
    public ResponseEntity<String> token(@RequestParam String sub) {
        Instant now = Instant.now();
        String jwt = Jwts.builder()
                .setSubject(sub)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(3600)))
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                .compact();
        return ResponseEntity.ok(jwt);
    }
}