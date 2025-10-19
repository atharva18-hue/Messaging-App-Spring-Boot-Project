package com.srdc.hw2.repository;

import com.srdc.hw2.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String username);


    Optional<User> findByUserNameAndPassword(String username, String password);


    @Query("SELECT u.userName FROM User u")
    List<String> findAllUsernames();


    /**
     * Finds users by a specified field and value with pagination.
     *
     * @param field The field to filter by.
     * @param value The value to filter by.
     * @param pageable The Pageable object for pagination.
     * @return A page of users that match the specified criteria.
     */
    @Query(value = "SELECT * FROM users u WHERE " +
            "(:field = 'username' AND LOWER(u.username) LIKE LOWER(CONCAT('%', :value, '%'))) OR " +
            "(:field = 'fullName' AND LOWER(u.full_name) LIKE LOWER(CONCAT('%', :value, '%'))) OR " +
            "(:field = 'email' AND LOWER(u.email) LIKE LOWER(CONCAT('%', :value, '%'))) OR " +
            "(:field = 'address' AND LOWER(u.address) LIKE LOWER(CONCAT('%', :value, '%'))) OR " +
            "(:field = 'gender' AND LOWER(u.gender) = LOWER(:value)) OR " +
            "(:field = 'birthDate' AND CAST(u.birth_date AS CHAR) LIKE CONCAT('%', :value, '%')) OR " +
            "(:field = 'role' AND ((u.is_admin = TRUE AND LOWER(:value) = 'admin') OR (u.is_admin = FALSE AND LOWER(:value) = 'user')))",
            countQuery = "SELECT count(*) FROM users u WHERE " +
                    "(:field = 'username' AND LOWER(u.username) LIKE LOWER(CONCAT('%', :value, '%'))) OR " +
                    "(:field = 'fullName' AND LOWER(u.full_name) LIKE LOWER(CONCAT('%', :value, '%'))) OR " +
                    "(:field = 'email' AND LOWER(u.email) LIKE LOWER(CONCAT('%', :value, '%'))) OR " +
                    "(:field = 'address' AND LOWER(u.address) LIKE LOWER(CONCAT('%', :value, '%'))) OR " +
                    "(:field = 'gender' AND LOWER(u.gender) = LOWER(:value)) OR " +
                    "(:field = 'birthDate' AND CAST(u.birth_date AS CHAR) LIKE CONCAT('%', :value, '%')) OR " +
                    "(:field = 'role' AND ((u.is_admin = TRUE AND LOWER(:value) = 'admin') OR (u.is_admin = FALSE AND LOWER(:value) = 'user')))",
            nativeQuery = true)
    Page<User> findUsersByFieldAndValue(
            @Param("field") String field,
            @Param("value") String value,
            Pageable pageable
    );



    @Modifying
    @Transactional
    @Query("UPDATE Message m SET m.fromUser = 'removed user' WHERE m.fromUser = :userName")
    void setFromUserToNull(@Param("userName") String userName);

    @Modifying
    @Transactional
    @Query("UPDATE Message m SET m.toUser = 'removed user' WHERE m.toUser = :userName")
    void setToUserToNull(@Param("userName") String userName);

    @Transactional
    default void deleteUserAndUpdateMessages(String userName) {
        setFromUserToNull(userName);
        setToUserToNull(userName);
        deleteByUserName(userName);
    }

    @Modifying
    @Transactional
    @Query("DELETE FROM User u WHERE u.userName = :userName")
    void deleteByUserName(@Param("userName") String userName);

}
