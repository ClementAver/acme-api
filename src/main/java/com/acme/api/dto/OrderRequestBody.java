package com.acme.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import java.time.LocalDate;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Validated
public class OrderRequestBody {
    @Email(message = "L'adresse email doit être valide.")
    String customerEmail;

    @PastOrPresent
    LocalDate date;

    public String toString() {
        log.info("mon objet");
        return super.toString();
    }
}


