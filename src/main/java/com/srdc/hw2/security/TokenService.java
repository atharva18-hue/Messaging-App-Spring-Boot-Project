package com.srdc.hw2.security;

import java.util.HashSet;
import java.util.Set;

public class TokenService {

    // A set to store active JWT tokens
    public static Set<String> activeTokens = new HashSet<>();

    /**
     * Adds a token to the set of active tokens.
     *
     * @param token The JWT token to be added.
     */
    public static void addToken(String token) {
        activeTokens.add(token);
    }

    /**
     * Checks if a token is active.
     *
     * @param token The JWT token to be checked.
     * @return True if the token is active, false otherwise.
     */
    public static boolean isActive(String token) {
        return activeTokens.contains(token);
    }

    /**
     * Removes a token from the set of active tokens.
     *
     * @param token The JWT token to be removed.
     */
    public static void removeToken(String token) {
        activeTokens.remove(token);
    }


    /**
     * Removes all tokens associated with a given username.
     *
     * @param username The username whose tokens are to be removed.
     */
    public static void removeTokenByUserName(String username) {
        activeTokens.removeIf(token -> username.equals(JwtUtil.getUsername(token)));
    }

}
