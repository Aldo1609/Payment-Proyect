package com.aldob.payment.dto.request;

import com.aldob.payment.dto.PaymentDetailDTO;
import com.aldob.payment.validation.anotations.ValidDivisa;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Datos de la solicitud de pago")
public class PaymentRequestDTO {

    @Schema(
            description = "Monto del pago en la divisa especificada",
            example = "250.50",
            minimum = "0.01"
    )
    @NotNull(message = "El monto es requerido")
    @Positive(message = "El monto debe ser mayor a cero")
    private double monto;

    @Schema(
            description = "Código de divisa ISO 4217",
            example = "USD",
            allowableValues = {"USD", "EUR", "MXN"}
    )
    @NotBlank(message = "La divisa es requerida")
    @ValidDivisa
    private String divisa;

    @Schema(
            description = "Identificador único del cliente",
            example = "5d9cdd4b-56a1-4751-8083-6086342a6242"
    )
    @NotBlank(message = "El ID del cliente es requerido")
    private String clienteId;

    @Schema(description = "Información de la tarjeta de pago")
    @Valid
    @NotNull(message = "Los detalles de pago son requeridos")
    private PaymentDetailDTO detallesPago;
}