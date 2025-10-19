package com.srdc.hw2.repository;

import com.srdc.hw2.model.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface MessageRepository extends JpaRepository<Message, Long> {


    /**
     * Finds messages in the user's mailbox based on the specified field and value, and filters by the type of mailbox (inbox or outbox).
     *
     * @param username The username of the user whose mailbox is being queried.
     * @param type The type of mailbox to filter (inbox or outbox).
     * @param field The field to filter by (optional).
     * @param value The value to filter by (optional).
     * @param pageable The Pageable object for pagination.
     * @return A page of messages that match the specified criteria.
     */
    @Query(value = "SELECT * FROM messages m WHERE " +
            "(:type = 'inbox' AND LOWER(m.to_user) = LOWER(:username)) OR " +
            "(:type = 'outbox' AND LOWER(m.from_user) = LOWER(:username)) AND " +
            "((:field IS NULL OR :value IS NULL) OR " +
            "(CASE WHEN :field = 'toUser' THEN LOWER(m.to_user) LIKE LOWER(CONCAT('%', :value, '%')) " +
            "WHEN :field = 'subject' THEN LOWER(m.subject) LIKE LOWER(CONCAT('%', :value, '%')) " +
            "WHEN :field = 'content' THEN LOWER(m.content) LIKE LOWER(CONCAT('%', :value, '%')) " +
            "ELSE true END))",
            nativeQuery = true)
    Page<Message> findMailboxByFieldAndValue(
            @Param("username") String username,
            @Param("type") String type,
            @Param("field") String field,
            @Param("value") String value,
            Pageable pageable
    );

}
