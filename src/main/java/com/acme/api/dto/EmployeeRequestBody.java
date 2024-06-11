package com.acme.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmployeeRequestBody {
    String firstName;
    String lastName;
    String email;
    String username;
    String password;

    public String toString() {
        log.info("mon objet");
        return super.toString();
    }
}


