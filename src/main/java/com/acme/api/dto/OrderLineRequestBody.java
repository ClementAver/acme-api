package com.acme.api.dto;

import com.acme.api.entities.Order;
import com.acme.api.entities.Product;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Validated
public class OrderLineRequestBody {
    // 100K unit max.
    @Min(value = 1, message = "Au moins 1 article doit être traité")
    Integer quantity;

    @NotBlank(message = "Un produit doit être associé.")
    String orderReference;

    @NotBlank(message = "Une commande doit être associée.")
    String productReference;
}


