package com.srdc.hw2.controller;

import com.srdc.hw2.exception.EntityNotFoundException;
import com.srdc.hw2.exception.UnauthorizedRequestException;
import com.srdc.hw2.model.Message;
import com.srdc.hw2.security.JwtUtil;
import com.srdc.hw2.security.TokenService;
import com.srdc.hw2.service.MessageService;
import com.srdc.hw2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping(value = "/api/v1")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;



    /**
     * Creates a new message.
     *
     * @param message The message object to be created.
     * @param token The JWT token of the user creating the message.
     * @return ResponseEntity containing the created message and a status of CREATED.
     * @throws UnauthorizedRequestException if the user is not authorized to perform this operation.
     * @throws EntityNotFoundException if the user has been deleted.
     */
    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message, @RequestHeader("Authorization") String token) {
        // Check if the token is active
        if (!TokenService.isActive(token)) {
            throw new UnauthorizedRequestException("User is not authorized to perform this operation");
        }

        // Get the username from the token
        String username = JwtUtil.getUsername(token);

        if (userService.findByUserName(username) == null) {
            throw new EntityNotFoundException("User has been deleted");
        }

        Message _message = messageService.createMessage(message);
        return new ResponseEntity<>(_message, HttpStatus.CREATED);
    }


    /**
     * Filters messages in the user's mailbox based on the specified criteria.
     *
     * @param page The page number to retrieve (default is 0).
     * @param size The number of messages per page (default is 10).
     * @param field The field to filter by (optional).
     * @param value The value to filter by (optional).
     * @param type The type of messages to filter. inbox or outbox
     * @param token The JWT token of the user requesting the messages.
     * @return ResponseEntity containing a page of filtered messages.
     * @throws UnauthorizedRequestException if the user is not authorized to perform this operation.
     * @throws EntityNotFoundException if the user has been deleted.
     */
    @GetMapping("/mailbox")
    public ResponseEntity<Page<Message>> filterMessages(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String field,
            @RequestParam(required = false) String value,
            @RequestParam String type,
            @RequestHeader("Authorization") String token) {
        if (!TokenService.isActive(token)) {
            throw new UnauthorizedRequestException("Please login first");
        }

        String username = JwtUtil.getUsername(token);

        if (userService.findByUserName(username) == null) {
            throw new EntityNotFoundException("User has been deleted");
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<Message> mailboxMessages = messageService.findMailboxByFieldAndValue(username, type, field, value, pageable);

        return ResponseEntity.ok(mailboxMessages);
    }

}
