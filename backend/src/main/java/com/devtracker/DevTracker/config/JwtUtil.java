package com.devtracker.DevTracker.config;

import com.devtracker.DevTracker.model.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    
    @Value("${jwt.secret}")
    private String secretKey;
    
    @Value("${jwt.expiration:86400000}")
    private long expirationTime;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String generateToken(User user){
        Integer orgId = null;
        if (user.getOrganization() != null) {
            orgId = user.getOrganization().getOrgId();
        }
        
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("userId", (Integer)user.getUserId())
                .claim("orgId", orgId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+expirationTime))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token){
        return parseToken(token).getBody().getSubject();
    }

    public boolean validateToken(String token){
        try{
            parseToken(token);
            return true;
        }catch (JwtException e){
            System.out.println("Token error: "+e.getMessage());
            return false;
        }
    }
    private Claims extractAllClaims(String token) {
        return parseToken(token).getBody();
    }
    public Integer extractUserId(String token){
        Claims claims =extractAllClaims(token);
        return claims.get("userId",Integer.class);
    }

    public Integer extractOrgId(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("orgId", Integer.class);
    }


    private Jws<Claims> parseToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token);

    }
}
