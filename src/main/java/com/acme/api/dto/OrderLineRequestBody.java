package com.acme.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderLineRequestBody {
    @Min(value = 1, message = "Au moins 1 article doit être traité")
    Integer quantity;

    @Pattern(regexp="^ORD-\\d{13}$", message = "Référence commande invalide.")
    @NotBlank(message = "Un produit doit être associé.")
    String orderReference;

    @Pattern(regexp="^PRO-\\d{13}$", message = "Référence produit invalide.")
    @NotBlank(message = "Une commande doit être associée.")
    String productReference;
}


