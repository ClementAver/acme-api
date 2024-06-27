package com.acme.api.dto;

import com.acme.api.entities.Order;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import java.util.Set;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Validated
public class CustomerRequestBody {
    @Size(max = 64, message = "Le prénom de doit pas dépasser 64 aractères.")
    String firstName;

    @Size(max = 128, message = "Le nom de doit pas dépasser 64 aractères.")
    String lastName;

    @Email(message = "L'adresse email doit être valide.")
    String email;

    @Pattern(regexp = "^\\d{10}$", message = "Le numéro de téléphone doit faire 10 chiffres.")
    String phone;

    @Size(max = 256, message = "L'addresse de doit pas dépasser 256 caractères.")
    String address;

    public String toString() {
        log.info("mon objet");
        return super.toString();
    }
}


