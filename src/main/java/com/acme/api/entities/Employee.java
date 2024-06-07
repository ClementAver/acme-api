package com.acme.api.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employees", schema = "acme")
public class Employee extends Individual {
    @Id
    @Column(name = "username", nullable = false, length = 128)
    private String username;

    @Column(name = "password", nullable = false, length = 64)
    private String password;

}