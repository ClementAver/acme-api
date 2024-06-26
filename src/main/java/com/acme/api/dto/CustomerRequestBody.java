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
    @Size(max = 64)
    String firstName;

    @Size(max = 128)
    String lastName;

    @Email(message = "Addresse email invalide.")
    String email;

    @Size(min = 10, max = 10)
    @Digits(integer = 10, fraction = 0)
    String phone;

    @Size(max = 128)
    String address;

    Set<Order> orders;

    public String toString() {
        log.info("mon objet");
        return super.toString();
    }
}


