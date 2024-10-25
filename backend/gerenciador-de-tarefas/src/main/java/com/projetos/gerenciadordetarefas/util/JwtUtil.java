package com.projetos.gerenciadordetarefas.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    private final String SECRET_KEY = "minha_chave_secreta"; // Troque por uma chave secreta forte

    // Metodo para gerar um token JWT
    public String gerarToken(String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // Metodo para extarir informações do token
    public String extrairUsername(String token) {
        return extrairClaims(token).getSubject();
    }

    // Metodo para extrair todos os dados do token
    private Claims extrairClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }
}
