package com.srdc.hw2.service;

import com.srdc.hw2.exception.BadRequestException;
import com.srdc.hw2.model.Message;
import com.srdc.hw2.repository.MessageRepository;
import com.srdc.hw2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;



@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;


    /**
     * Creates a new message.
     *
     * @param message The message object containing the details of the message to be created.
     * @return The created Message object.
     * @throws BadRequestException if the sender or recipient user does not exist.
     */
    @Override
    public Message createMessage(Message message) {
        if (userRepository.findByUserName(message.getFromUser()).isEmpty()) {
            throw new BadRequestException("User does not exist");
        }

        if (userRepository.findByUserName(message.getToUser()).isEmpty()) {
            throw new BadRequestException("You cannot send a message to non-existing user");
        }

        Message _message = new Message(
                message.getFromUser(),
                message.getToUser(),
                message.getSubject(),
                message.getContent()
        );
        return messageRepository.save(_message);
    }


    /**
     * Finds messages in the user's mailbox based on the specified field and value.
     *
     * @param username The username of the user whose mailbox is being queried.
     * @param type The type of messages to filter.
     * @param field The field to filter by (optional).
     * @param value The value to filter by (optional).
     * @param pageable The Pageable object for pagination.
     * @return A page of messages that match the specified criteria.
     */
    @Override
    public Page<Message> findMailboxByFieldAndValue(String username, String type, String field, String value, Pageable pageable) {
        return messageRepository.findMailboxByFieldAndValue(username, type, field, value, pageable);
    }

}
