package com.gustavosdaniel.authservice.commun;

import com.gustavosdaniel.authservice.user.LoginRequestDTO;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecretJwk;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    private final Key secretKey;

    public JwtUtil(@Value("${jwt.secret}") String secret) {

        byte[] keyBytes = Base64.getDecoder().decode(secret.getBytes(
                StandardCharsets.UTF_8
        ));

        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String email, String role) {
        return Jwts.builder()
                .subject(email) // dentificador único do usuário
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))  // 10 horas
                .signWith(secretKey) // garante que o token é autêntico e que seu conteúdo não foi modificado por terceiros
                .compact();
    }

    public void validateToken(String token) {

        try {

            Jwts.parser().verifyWith((SecretKey) secretKey)
                    .build()
                    .parseSignedClaims(token);

        } catch (SignatureException ex) { // erro de assinatura inválida

            throw new JwtException("Invalid JWT signature");

        }catch (JwtException exception){ // outro erro relacionado ao JWT (expirado, formato inválido, etc.).

            throw new JwtException("Invalid JWT token");
        }
    }
}
