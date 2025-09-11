package com.aldob.payment.consumer.service.impl;

import com.aldob.payment.consumer.entity.Pagos;
import com.aldob.payment.consumer.repository.PagoRepository;
import com.aldob.payment.consumer.service.PaymentProcessService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class PaymentProcessServiceImpl implements PaymentProcessService {

    private final PagoRepository pagoRepository;
    private final StripeService stripeService;

    @Override
    public void paymentProcess(String uuidPago) {
        try {
            Pagos pagos = pagoRepository.findById(UUID.fromString(uuidPago))
                    .orElseThrow(() -> new RuntimeException("El ID ingresado no se encontro: " + uuidPago));

            Long amountInCents = (long) (pagos.getMonto() * 100);
            String paymentMethodId = getStripePaymentMethodId(pagos.getCard());

            PaymentIntent paymentIntent = stripeService.createPaymentIntent(
                    amountInCents,
                    pagos.getDivisa(),
                    paymentMethodId
            );

            if ("succeeded".equals(paymentIntent.getStatus())){
                pagos.setEstado("APROBADO");
                pagos.setIdTransaccionPasarela(paymentIntent.getId());
                pagos.setFechaActualizacion(new Timestamp(System.currentTimeMillis()));
                log.info("Pago {} aprobado por Stripe: {}", uuidPago, paymentIntent.getId());
            } else {
                pagos.setEstado("RECHAZADO");
                pagos.setMensajeError("Stripe: " + paymentIntent.getLastPaymentError());
                log.warn("Pago {} rechazado por Stripe: {}", uuidPago, paymentIntent.getStatus());
            }

            pagoRepository.save(pagos);
        }catch (StripeException e){
            log.error("Error procesando pago {} con Stripe: {}", uuidPago, e.getMessage());
            throw new RuntimeException("Error en pasarela de pago: " + e.getMessage());
        }catch (Exception e) {
            log.error("Error inesperado procesando pago {}: {}", uuidPago, e.getMessage());
            throw new RuntimeException("Error interno: " + e.getMessage());
        }

    }

    private String getStripePaymentMethodId(String cardType) {
        return switch (cardType.toLowerCase()) {
            case "visa" -> "pm_card_visa";
            case "mastercard" -> "pm_card_mastercard";
            case "amex" -> "pm_card_amex";
            default -> {
                log.warn("Tipo de tarjeta no esperado: {}, usando Visa por defecto", cardType);
                yield "pm_card_visa";
            }
        };
    }

}
