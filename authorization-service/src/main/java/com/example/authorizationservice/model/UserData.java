package com.example.authorizationservice.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "user_data")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "mobile_number", nullable = false)
    private String mobileNumber;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String pin;

    @Column(nullable = false)
    private String role = "USER";
}
