package com.aldob.payment.service;

import com.aldob.payment.dto.request.PaymentRequestDTO;
import com.aldob.payment.entity.Pagos;

public interface ProcessPaymentService {

    Pagos processPayment(PaymentRequestDTO paymentRequestDTO);

}
