package com.movies_management.Services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JWTService {

    private final String secretKey;

    public JWTService() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk = keyGen.generateKey();
            secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    // ✅ Ensure roles are included when generating token
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        // ✅ Extract roles correctly
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());


        claims.put("roles", roles); // ✅ Ensure roles are added
        return generateToken(claims, userDetails.getUsername());
    }

    // ✅ Overloaded method for simple token generation
    public String generateToken(String username) {
        return generateToken(new HashMap<>(), username); // Call correct method
    }

    // ✅ Core token generation logic
    private String generateToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(username) // Store username
                .issuedAt(new Date(System.currentTimeMillis())) // Token creation time
                .expiration(new Date(System.currentTimeMillis() + (60 * 60 * 1000))) // 1-hour expiry
                .and()
                .signWith(getKey()) // Sign with secret key
                .compact();
    }

    // Secret key conversion
    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // ✅ Ensure correct type casting when extracting roles
    public List<String> extractRoles(String token) {
        Claims claims = extractAllClaims(token);
        Object rolesObject = claims.get("roles");

        if (rolesObject instanceof List<?>) {
            return ((List<?>) rolesObject).stream()
                    .map(Object::toString) // Convert to string list
                    .collect(Collectors.toList());
        }

        return Collections.emptyList(); // Return empty list if no roles found
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    // Extract all claims from token
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // Validate token by checking username and expiration
    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
