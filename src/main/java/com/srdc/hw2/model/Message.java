package com.srdc.hw2.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

// TODO user gender string cevir, message ve user icin not blank validation ekle
// TODO send message implement et


@Entity
@Table(name = "messages")
@Getter
@Setter
@RequiredArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "from_user")
    @NotBlank(message = "fromUser field cannot be empty")
    private String fromUser;

    @Column(name = "to_user")
    @NotBlank(message = "toUser field cannot be empty")
    private String toUser;

    @Column(name = "send_date")
    private Timestamp sendDate;

    @Column(name = "subject")
    @NotBlank(message = "subject field cannot be empty")
    private String subject;

    @Column(name = "content")
    @NotBlank(message = "content field cannot be empty")
    private String content;

    @Column(name = "is_read")
    private boolean isRead;


    public Message(String fromUser, String toUser, String subject, String content) {
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.sendDate = new Timestamp(System.currentTimeMillis());
        this.subject = subject;
        this.content = content;
    }
}