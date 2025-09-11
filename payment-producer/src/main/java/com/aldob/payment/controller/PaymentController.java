package com.aldob.payment.controller;

import com.aldob.payment.dto.payload.Meta;
import com.aldob.payment.dto.request.PaymentRequestDTO;
import com.aldob.payment.entity.Pagos;
import com.aldob.payment.service.ProcessPaymentService;
import com.aldob.payment.utils.GenericResponse;
import com.aldob.payment.utils.MetaGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.aldob.payment.utils.AppMessages.ACTION_OK;

@RestController
@RequestMapping("/payments")
@AllArgsConstructor
@Tag(name = "Payments", description = "Gestión y procesamiento de pagos con tarjetas")
public class PaymentController {

    private final ProcessPaymentService processPaymentService;
    private final MetaGenerator metaGenerator;

    @Operation(
            summary = "Procesar pago con tarjeta de crédito",
            description = "Procesa un pago validando los datos de la tarjeta y enviándolo a la pasarela de pago"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pago procesado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<GenericResponse<Pagos>> processPayment(
            @Valid @RequestBody PaymentRequestDTO paymentRequestDTO) {

        Pagos processedPayment = processPaymentService.processPayment(paymentRequestDTO);
        final Meta meta = metaGenerator.crearMetaObject(HttpStatus.OK, ACTION_OK);

        GenericResponse<Pagos> response = new GenericResponse<>(meta, processedPayment);
        return ResponseEntity.ok(response);
    }
}