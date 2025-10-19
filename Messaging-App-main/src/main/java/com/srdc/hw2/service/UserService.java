package com.srdc.hw2.service;


import com.srdc.hw2.model.Message;
import com.srdc.hw2.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {


    Page<User> getAllUsers(int page, int size);

    User findByUserName(String username);

    User findByUserNameAndPassword(String username, String password);

    User createUser(User user);

    void deleteUserByUsername(String deleteUsername);

    void updateUserByFieldName(String username, String field, String value);

    Page<User> findUsersByFieldAndValue(Pageable pageable, String field, String value);

    List<String> getAllUsernames();
}
