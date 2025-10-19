package com.srdc.hw2.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import com.srdc.hw2.validation.Gender;

import java.sql.Date;

@Entity
@Table(name = "users")
@Getter
@Setter
@RequiredArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  long id;

    @Column(name = "username", unique = true)
    @NotBlank(message = "username field cannot be blank")
    private String userName;

    @Column(name = "password")
    @NotBlank(message = "password field cannot be blank")
    private String password;

    @Column(name = "full_name")
    @NotBlank(message = "full name field cannot be blank")
    private String fullName;

    //@Past(message = "Birth date must be in the past")
    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "gender")
    @Gender
    private String gender;

    @Column(name = "address")
    @NotBlank(message = "address field cannot be blank")
    private String address;

    @Column(name = "email")
    @NotBlank(message = "email field cannot be blank")
    private String email;

    @Column(name = "is_admin")
    private boolean isAdmin;



    public User(String userName, String password, String fullName, Date birthDate, String gender, String address, String email, boolean admin) {
        this.userName = userName;
        this.password = password;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.address = address;
        this.email = email;
        this.isAdmin = admin;
    }

}

