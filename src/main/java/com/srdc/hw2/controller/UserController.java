package com.srdc.hw2.controller;

import com.srdc.hw2.exception.BadRequestException;
import com.srdc.hw2.exception.EntityNotFoundException;
import com.srdc.hw2.exception.ForbiddenRequestException;
import com.srdc.hw2.exception.UnauthorizedRequestException;
import com.srdc.hw2.model.User;
import com.srdc.hw2.security.JwtUtil;
import com.srdc.hw2.security.TokenService;
import com.srdc.hw2.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping(value = "/api/v1")
public class UserController {


    @Autowired
    private UserService userService;




    /**
     * Retrieves a paginated list of users, optionally filtered by a specified field and value.
     *
     * @param page The page number to retrieve (default is 0).
     * @param size The number of users per page (default is 10).
     * @param field The field to filter by (optional).
     * @param value The value to filter by (optional).
     * @param token The JWT token of the requesting user.
     * @return ResponseEntity containing a page of users.
     * @throws UnauthorizedRequestException if the user is not authorized.
     * @throws ForbiddenRequestException if the user does not have permission to access this resource.
     * @throws EntityNotFoundException if the requesting user has been deleted.
     */
    @GetMapping("/users")
    public ResponseEntity<Page<User>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String field,
            @RequestParam(required = false) String value,
            @RequestHeader("Authorization") String token) {
        if (!TokenService.isActive(token)) {
            throw new UnauthorizedRequestException("Please login first");
        }

        if (!JwtUtil.isAdmin(token)) {
            throw new ForbiddenRequestException("You do not have permission to access this resource");
        }

        String username = JwtUtil.getUsername(token);
        if (userService.findByUserName(username) == null) {
            throw new EntityNotFoundException("User has been deleted");
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<User> users;
        if (field != null && value != null) {
            users = userService.findUsersByFieldAndValue(pageable, field, value);
        } else {
            users = userService.getAllUsers(pageable.getPageNumber(), pageable.getPageSize());
        }

        return new ResponseEntity<>(users, HttpStatus.OK);
    }



    /**
     * Creates a new user.
     *
     * @param token The JWT token of the requesting user.
     * @param user The user object to be created.
     * @param bindingResult The binding result to hold validation errors.
     * @return ResponseEntity containing the created user.
     * @throws UnauthorizedRequestException if the user is not authorized.
     * @throws ForbiddenRequestException if the user does not have permission to create a new user.
     * @throws EntityNotFoundException if the requesting user has been deleted.
     * @throws BadRequestException if there are validation errors in the user object.
     */
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestHeader("Authorization") String token, @Valid @RequestBody User user, BindingResult bindingResult) {
        if (!TokenService.isActive(token)) {
            throw new UnauthorizedRequestException("Please login first");
        }

        if (!JwtUtil.isAdmin(token)) {
            throw new ForbiddenRequestException("You do not have permission to delete this resource");
        }

        String username = JwtUtil.getUsername(token);
        if (userService.findByUserName(username) == null) {
            throw new EntityNotFoundException("User has been deleted");
        }

        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getFieldError().getDefaultMessage());
        }
        User _user = userService.createUser(user);
        return new ResponseEntity<>(_user, HttpStatus.CREATED);

    }



    /**
     * Deletes a user by username.
     *
     * @param token The JWT token of the requesting user.
     * @param username The username of the user to be deleted.
     * @return ResponseEntity containing a success message.
     * @throws UnauthorizedRequestException if the user is not authorized.
     * @throws ForbiddenRequestException if the user does not have permission to delete this resource.
     * @throws EntityNotFoundException if the requesting user has been deleted.
     */
    @DeleteMapping("/users/{username}")
    public ResponseEntity<String> deleteUser(@RequestHeader("Authorization") String token, @PathVariable String username) {
        if (!TokenService.isActive(token)) {
            throw new UnauthorizedRequestException("Please login first");
        }

        if (!JwtUtil.isAdmin(token)) {
            throw new ForbiddenRequestException("You do not have permission to delete this resource");
        }

        String curUsername = JwtUtil.getUsername(token);
        if (userService.findByUserName(curUsername) == null) {
            throw new EntityNotFoundException("User has been deleted");
        }

        userService.deleteUserByUsername(username);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }




    /**
     * Updates a user's field with a new value.
     *
     * @param token The JWT token of the requesting user.
     * @param username The username of the user to be updated.
     * @param field The field to be updated.
     * @param value The new value for the field.
     * @return ResponseEntity containing a success message.
     * @throws UnauthorizedRequestException if the user is not authorized.
     * @throws ForbiddenRequestException if the user does not have permission to update this resource.
     * @throws EntityNotFoundException if the requesting user has been deleted.
     */
    @PutMapping("/users")
    public ResponseEntity<String> updateUser(@RequestHeader("Authorization") String token, @RequestParam(name = "username") String username,
                                             @RequestParam(name = "field") String field, @RequestParam(name = "value") String value) {
        if (!TokenService.isActive(token)) {
            throw new UnauthorizedRequestException("Please login first");
        }
        if (!JwtUtil.isAdmin(token)) {
            throw new ForbiddenRequestException("You do not have permission to delete this resource");
        }

        String curUsername = JwtUtil.getUsername(token);
        if (userService.findByUserName(curUsername) == null) {
            throw new EntityNotFoundException("User has been deleted");
        }

        userService.updateUserByFieldName(username,field, value);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }




    /**
     * Retrieves a list of all usernames.
     *
     * @param token The JWT token of the requesting user.
     * @return ResponseEntity containing a list of all usernames.
     * @throws UnauthorizedRequestException if the user is not authorized.
     */
    @GetMapping("/usernames")
    public ResponseEntity<List<String>> getAllUsernames(@RequestHeader("Authorization") String token) {

        if (!TokenService.isActive(token)) {
            throw new UnauthorizedRequestException("Please login first");
        }

        List<String> usernames = userService.getAllUsernames();
        return ResponseEntity.ok(usernames);
    }
}
