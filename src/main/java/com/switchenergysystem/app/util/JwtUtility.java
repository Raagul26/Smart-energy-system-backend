package com.switchenergysystem.app.util;

import com.switchenergysystem.app.entity.User;
import com.switchenergysystem.app.repository.UserRepo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtility implements Serializable {

    @Autowired
    private UserRepo userRepository;

    private static final long serialVersionUID = -2550185165626007488L;

    private final String secret = "secret";

    //retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    //retrieve username from jwt token
    public Date getIssuedDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getIssuedAt);
    }

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //for retrieving any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        }catch (ExpiredJwtException e)
        {
            throw new RuntimeException("JWT Token Expired");
        }
    }

    //check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //generate token for user
    public String generateToken(String emailId, long jwtTokenValidity) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, emailId, jwtTokenValidity);
    }

    private String doGenerateToken(Map<String, Object> claims, String subject, long jwtTokenValidity) {
        return Jwts.builder().setClaims(claims).claim("role",userRepository.findByEmailId(subject).getRole()).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtTokenValidity * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public String validateToken(String token) {
        try {
            final Claims claims = getAllClaimsFromToken(token);
            User user= userRepository.findByEmailId(claims.getSubject());
            if(claims.get("role").equals("user") && user != null && !isTokenExpired(token)) {
                return "user";
            }
            else if(claims.get("role").equals("admin") && user != null && !isTokenExpired(token))
            {
                return "admin";
            }
            else {
                return "";
            }
        }
        catch (IllegalArgumentException | SignatureException | MalformedJwtException e)
        {
            return "";
        }
    }
}
