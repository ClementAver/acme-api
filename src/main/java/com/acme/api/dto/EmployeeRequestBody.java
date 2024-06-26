package com.acme.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmployeeRequestBody {
    @Size(max = 64, message = "Le prénom de doit pas dépasser 64 aractères.")
    String firstName;

    @Size(max = 128, message = "Le nom de doit pas dépasser 64 aractères.")
    String lastName;

    @Email(message = "L'adresse email doit être valide.")
    String email;

    @Size(max = 64, message = "Le pseudonyme ne doit pas dépasser 64 caractères.")
    String username;

    @Size(max = 256, message = "Le mot de passe ne doit pas dépasser 256 caractères.")
    String password;
}


