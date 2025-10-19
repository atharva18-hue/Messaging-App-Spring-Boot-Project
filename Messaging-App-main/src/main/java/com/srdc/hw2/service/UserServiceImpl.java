package com.srdc.hw2.service;


import com.srdc.hw2.exception.BadRequestException;
import com.srdc.hw2.exception.EntityNotFoundException;
import com.srdc.hw2.model.User;
import com.srdc.hw2.repository.MessageRepository;
import com.srdc.hw2.repository.UserRepository;
import com.srdc.hw2.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements  UserService {

    @Autowired
    private UserRepository userRepository;





    /**
     * Finds a user by their username.
     *
     * @param username The username of the user to find.
     * @return The found User object.
     * @throws EntityNotFoundException if the user with the specified username is not found.
     */
    @Override
    public User findByUserName(String username) {
        Optional<User> user = userRepository.findByUserName(username);
        if (user.isEmpty()) {
            throw new EntityNotFoundException("User with username " + username + " not found");
        }
        return user.get();
    }


    /**
     * Finds a user by their username and password.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @return The found User object.
     * @throws EntityNotFoundException if the user with the specified credentials is not found.
     */
    @Override
    public User findByUserNameAndPassword(String username, String password) {
        Optional<User> user = userRepository.findByUserNameAndPassword(username, password);
        if (user.isEmpty()) {
            throw new EntityNotFoundException("User with the given credentials not found");
        }
        return user.get();
    }



    /**
     * Creates a new user.
     *
     * @param user The user object containing the details of the user to be created.
     * @return The created User object.
     * @throws BadRequestException if a user with the specified username already exists.
     */
    @Override
    public User createUser(User user) {
        Optional<User> checkUser = userRepository.findByUserName(user.getUserName());
        if (checkUser.isPresent()) {
            throw new BadRequestException("User name " + user.getUserName() + " already exists");
        }

        User theUser = new User(
                    user.getUserName(),
                    user.getPassword(),
                    user.getFullName(),
                    user.getBirthDate(),
                    user.getGender(),
                    user.getAddress(),
                    user.getEmail(),
                    user.isAdmin()
            );

        return userRepository.save(theUser);
    }



    /**
     * Deletes a user by their username and updates related messages.
     *
     * @param deleteUsername The username of the user to be deleted.
     */
    @Override
    public void deleteUserByUsername(String deleteUsername) {
        userRepository.deleteUserAndUpdateMessages(deleteUsername);
        TokenService.removeTokenByUserName(deleteUsername);
    }


    /**
     * Updates a specific field of a user.
     *
     * @param username The username of the user to be updated.
     * @param field The field to be updated.
     * @param value The new value for the field.
     */
    @Override
    public void updateUserByFieldName(String username, String field, String value) {
        User updateUser = findByUserName(username);
        switch (field) {
            case "fullName":
                updateUser.setFullName(value);
                break;
            case "email":
                updateUser.setEmail(value);
                break;
            case "address":
                updateUser.setAddress(value);
                break;
            case "gender":
                updateUser.setGender(value.toUpperCase());
                break;
            case "birthDate":
                updateUser.setBirthDate(Date.valueOf(value));
                break;
            case "role":
                updateUser.setAdmin(value.equals("admin"));
                break;
        }
        userRepository.save(updateUser);
    }


    /**
     * Finds users by a specified field and value with pagination.
     *
     * @param pageable The Pageable object for pagination.
     * @param field The field to filter by.
     * @param value The value to filter by.
     * @return A page of users that match the specified criteria.
     */
    @Override
    public Page<User> findUsersByFieldAndValue(Pageable pageable, String field, String value) {
        return userRepository.findUsersByFieldAndValue(field, value, pageable);
    }


    /**
     * Retrieves all usernames.
     *
     * @return A list of all usernames.
     */
    @Override
    public List<String> getAllUsernames() {
        return userRepository.findAllUsernames();
    }


    /**
     * Retrieves all users with pagination.
     *
     * @param page The page number to retrieve.
     * @param size The number of users per page.
     * @return A page of users.
     */
    @Override
    public Page<User> getAllUsers(int page, int size) {
        return userRepository.findAll(PageRequest.of(page, size));
    }

}
