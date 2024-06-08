package com.acme.api.dto;

import com.acme.api.entities.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import java.util.Set;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerRequestBody {
    String firstName;
    String lastName;
    String phone;
    String address;
    String email;
    Set<Order> orders;

    public String toString() {
        log.info("mon objet");
        return super.toString();
    }
}


