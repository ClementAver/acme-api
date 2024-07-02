package com.acme.api.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoginRequestBody {
    @Size(max = 128, message = "Le prénom de doit pas dépasser 64 aractères.")
    private String username;
    @Size(max = 256, message = "Le prénom de doit pas dépasser 64 aractères.")
    private String password;
}
