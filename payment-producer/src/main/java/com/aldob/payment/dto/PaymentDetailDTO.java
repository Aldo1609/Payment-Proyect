package com.aldob.payment.dto;

import com.aldob.payment.validation.anotations.ValidCardDetails;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ValidCardDetails
@Schema(description = "Detalles de la tarjeta de pago")
public class PaymentDetailDTO {

    @Schema(
            description = "Número de tarjeta de crédito sin espacios ni guiones",
            example = "4532015112830366"
    )
    @NotBlank(message = "Número de tarjeta es requerido")
    private String numeroTarjeta;

    @Schema(
            description = "Código de verificación (3 dígitos para Visa/MC, 4 para Amex)",
            example = "123"
    )
    @NotBlank(message = "CVV es requerido")
    private String cvv;

    @Schema(
            description = "Fecha de expiración en formato MM/YY",
            example = "12/25",
            pattern = "^(0[1-9]|1[0-2])/[0-9]{2}$"
    )
    @NotBlank(message = "Fecha de expiración es requerida")
    @Pattern(regexp = "^(0[1-9]|1[0-2])/[0-9]{2}$", message = "Formato de fecha debe ser MM/YY")
    private String fechaExpiracion;
}