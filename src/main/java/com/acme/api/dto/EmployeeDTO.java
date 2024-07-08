package com.acme.api.dto;

import com.acme.api.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmployeeDTO {
    String firstName;
    String lastName;
    String email;
    String username;
    Role role;
}


