package com.elf.app.services;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    @Value("${elf.jwt-secret-token}")
    private String secretToken;
    public static final long TOKEN_FINISH_TIME = 1000 * 60 * 60 * 24;

    /**
     * Get the key used to sign the JWT token.
     * 
     * @return the key used to sign the JWT token
     */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretToken);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Extracts the username from the JWT token.
     * 
     * @param token the JWT token
     * @return the username
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Generates a JWT token.
     * 
     * @param extraClaims extra claims to add to the JWT token
     * @param user        the user to generate the token for
     * @return the JWT token
     */
    public String generateToken(Map<String, Object> extraClaims, UserDetails user) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_FINISH_TIME))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256).compact();
    }

    /**
     * Generates a JWT token.
     * 
     * @param user the user to generate the token for
     * @return the JWT token
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Checks if the JWT token is valid
     * 
     * @param token       the JWT token
     * @param userDetails the user details
     * @return true if the JWT token is valid
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * Checks if the JWT token is expired.
     * 
     * @param token the JWT token
     * @return true if the JWT token is expired
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracts the expiration date from the JWT token.
     * 
     * @param token the JWT token
     * @return the expiration date
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extracts the clains from the JWT token.
     * 
     * @param token the JWT token
     * @return body of JWT token
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
    }

    /**
     * Extract a claim from the JWT token.
     * 
     * @param <T>            the type of the claim
     * @param token          the JWT token
     * @param claimsResolver the function to extract the claim
     * @return the claim
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

}
