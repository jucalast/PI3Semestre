package com.app.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Serviço responsável pela geração, validação e extração de informações de tokens JWT.
 *
 * Esta classe fornece métodos para criar tokens JWT, extrair informações dos tokens e verificar sua validade.
 * Utiliza uma chave secreta para assinatura e validação dos tokens.
 */
@Service
public class JwtService {

    private static final String SECRET_KEY = "2338e79a6fd7c92ed41967ecd1a43206e6e91db6af537660f91a4702d055eb98";

    /**
     * Extrai o nome de usuário do token JWT.
     *
     * @param token O token JWT.
     * @return O nome de usuário extraído do token JWT.
     */
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extrai uma reivindicação (claim) do token JWT.
     *
     * @param token O token JWT.
     * @param claimsResolver O resolvedor de reivindicações. que é uma função que recebe um objeto {@link Claims} e retorna um valor.
     * @param <T> O tipo de reivindicação.
     * @return A reivindicação extraída do token JWT.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Gera um token JWT para um usuário com base nos detalhes do usuário fornecidos.
     *
     * @param userDetails Os detalhes do usuário.
     * @return O token JWT gerado.
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Gera um token JWT para um usuário com base nos detalhes do usuário fornecidos e reivindicações extras.
     *
     * @param extraClaims As reivindicações extras.
     * @param userDetails Os detalhes do usuário.
     * @return O token JWT gerado.
     */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Verifica se um token JWT é válido para um usuário com base nos detalhes do usuário fornecidos.
     *
     * @param token O token JWT.
     * @param userDetails Os detalhes do usuário.
     * @return {@code true} se o token JWT for válido para o usuário, {@code false} caso contrário.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUserName(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Verifica se um token JWT está expirado sua data de validade.
     *
     * @param token O token JWT.
     * @return {@code true} se o token JWT estiver expirado, {@code false} caso contrário.
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extrai a data de validade do token JWT.
     *
     * @param token O token JWT.
     * @return A data de validade extraída do token JWT.
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extrai todas as declarações (claims) de um token JWT.
     *
     * Este método utiliza a chave de assinatura configurada para analisar o token JWT e extrair suas
     * declarações. Ele é responsável por validar o token e fornecer o corpo das declarações contidas nele.
     *
     * @param token O token JWT do qual as declarações serão extraídas.
     * @return O corpo das declarações (claims) extraídas do token JWT.
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Obtém a chave de assinatura usada para validar tokens JWT.
     *
     * Este método decodifica a chave secreta codificada em Base64 e a converte em um objeto `Key`
     * que é utilizado para a assinatura e verificação dos tokens JWT.
     *
     * @return A chave de assinatura configurada para a validação de tokens JWT.
     */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}