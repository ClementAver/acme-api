package com.acme.api.dto;

import com.acme.api.entities.OrderLine;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;

import java.util.Set;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Validated
public class ProductRequestBody {
    @Size(max = 64)
    String name;

    @Digits(integer = 8, fraction = 0)
    Integer price;

    public String toString() {
        log.info("mon objet");
        return super.toString();
    }
}


