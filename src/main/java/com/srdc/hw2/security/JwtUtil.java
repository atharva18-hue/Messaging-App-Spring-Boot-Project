package com.srdc.hw2.security;

import com.srdc.hw2.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.sql.Date;


public class JwtUtil {

    // Expiration time for the JWT token (1 hour)
    private static final long EXPIRATION_TIME = 3600000;

    // Secret key for signing the JWT token
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);


    /**
     * Generates a JWT token for the given user.
     *
     * @param user The user for whom the token is being generated.
     * @return The generated JWT token.
     */
    public static String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUserName())
                .claim("isAdmin", user.isAdmin())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    /**
     * Checks if the user represented by the given token has admin privileges.
     *
     * @param token The JWT token to be checked.
     * @return True if the user has admin privileges, false otherwise.
     */
    public static boolean isAdmin(String token) {
        try {
            return (Boolean) Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .get("isAdmin");
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * Extracts the username from the given JWT token.
     *
     * @param token The JWT token from which the username is to be extracted.
     * @return The username if extraction is successful, null otherwise.
     */
    public static String getUsername(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            return null;
        }
    }
}
