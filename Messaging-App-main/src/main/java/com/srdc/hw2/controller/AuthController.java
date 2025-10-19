package com.srdc.hw2.controller;

import com.srdc.hw2.model.User;
import com.srdc.hw2.security.JwtUtil;
import com.srdc.hw2.security.TokenService;
import com.srdc.hw2.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;


    /**
     * Authenticates a user using their username and password.
     * If authentication is successful, a JWT token is generated and returned.
     *
     * @param user The user object containing the username and password.
     * @return ResponseEntity containing the JWT token if authentication is successful,
     *         or an unauthorized status if authentication fails.
     */
    @PostMapping("/users/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        // Find the user by username and password
        User loginUser = userService.findByUserNameAndPassword(user.getUserName(), user.getPassword());

        if (loginUser != null) {
            // Generate a JWT token for the authenticated user
            String token = JwtUtil.generateToken(loginUser);
            // Add the token to the active tokens list
            TokenService.addToken(token);
            // Return the JWT token in the response
            return new ResponseEntity<>(token, HttpStatus.OK);
        } else {
            // Return an unauthorized status if authentication fails
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Logs out a user by invalidating the provided JWT token.
     *
     * @param token The JWT token to be invalidated.
     * @return ResponseEntity containing a success message.
     */
    @GetMapping("/users/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) {
        TokenService.removeToken(token);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

}
