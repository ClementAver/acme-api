package com.acme.api.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employees")
public class Employee extends Individual {
    @Column(name = "username", nullable = false, length = 128, unique = true)
    private String username;

    @Column(name = "password", nullable = false, length = 64)
    private String password;

}