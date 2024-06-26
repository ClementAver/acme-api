package com.acme.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Validated
public class EmployeeRequestBody {
    @Size(max = 64)
    String firstName;

    @Size(max = 128)
    String lastName;

    @Email(message = "Addresse email invalide.")
    String email;

    @Size(max = 64)
    String username;

    @Size(max = 256)
    String password;

    public String toString() {
        log.info("mon objet");
        return super.toString();
    }
}


