package com.aldob.payment.service.impl;

import com.aldob.payment.dto.request.PaymentRequestDTO;
import com.aldob.payment.entity.Pagos;
import com.aldob.payment.mapper.PaymentMapper;
import com.aldob.payment.repository.PagosRepository;
import com.aldob.payment.service.ProcessPaymentService;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ProcessPaymentServiceImpl implements ProcessPaymentService {

    private final PaymentMapper mapper;
    private final PagosRepository repository;
    private KafkaTemplate<String,Object> kafkaTemplate;

    @Override
    @Transactional
    public Pagos processPayment(PaymentRequestDTO paymentRequestDTO) {
        Pagos savedPagos = repository.save(mapper.toEntity(paymentRequestDTO));
        kafkaTemplate.send("payments-topic", savedPagos.getId().toString());
        return savedPagos;
    }




}
