package com.acme.api.dto;

import com.acme.api.entities.Customer;
import com.acme.api.entities.OrderLine;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.Set;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Validated
public class OrderRequestBody {
    Customer idCustomer;

    @PastOrPresent
    LocalDate date;

    Set<OrderLine> orderLines;

    public String toString() {
        log.info("mon objet");
        return super.toString();
    }
}


