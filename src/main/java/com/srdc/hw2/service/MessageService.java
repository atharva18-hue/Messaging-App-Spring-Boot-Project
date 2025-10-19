package com.srdc.hw2.service;

import com.srdc.hw2.model.Message;
import com.srdc.hw2.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface MessageService {

    Message createMessage(Message message);

    Page<Message> findMailboxByFieldAndValue(String username, String type, String field, String value, Pageable pageable);
}
