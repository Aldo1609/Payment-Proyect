package com.aldob.payment.mapper;

import com.aldob.payment.dto.request.PaymentRequestDTO;
import com.aldob.payment.entity.Clientes;
import com.aldob.payment.entity.Pagos;
import com.aldob.payment.exception.ClienteNotFoundException;
import com.aldob.payment.exception.InvalidCardException;
import com.aldob.payment.exception.InvalidUUIDException;
import com.aldob.payment.repository.ClientesRepository;
import com.aldob.payment.utils.CardTypeDetector;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.UUID;

@Component
@AllArgsConstructor
public class PaymentMapper {

    private final ClientesRepository clientesRepository;

    public Pagos toEntity(PaymentRequestDTO request) {
        UUID clienteId = validateAndConvertUUID(request.getClienteId());

        Clientes cliente = clientesRepository.findById(clienteId)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente no encontrado con ID: " + request.getClienteId()));

        String cardType = validateAndDetectCardType(request.getDetallesPago().getNumeroTarjeta());

        Pagos pago = new Pagos();
        pago.setMonto(request.getMonto());
        pago.setDivisa(request.getDivisa());
        pago.setCard(cardType);
        pago.setCliente(cliente);
        pago.setEstado("PENDIENTE");
        pago.setFechaCreacion(new Timestamp(System.currentTimeMillis()));

        return pago;
    }

    private UUID validateAndConvertUUID(String uuidString) {
        try {
            return UUID.fromString(uuidString);
        } catch (IllegalArgumentException e) {
            throw new InvalidUUIDException("ID de cliente inválido: " + uuidString + ". Debe ser un UUID válido.");
        }
    }

    private String validateAndDetectCardType(String cardNumber) {
        if (cardNumber == null || cardNumber.trim().isEmpty()) {
            throw new InvalidCardException("Número de tarjeta es requerido");
        }

        String cardType = CardTypeDetector.detectCardType(cardNumber);

        if (!CardTypeDetector.isCardTypeSupported(cardType)) {
            throw new InvalidCardException("Tipo de tarjeta no soportado: " + cardType +
                    ". Solo aceptamos Visa, Mastercard y American Express.");
        }

        return cardType;
    }
}