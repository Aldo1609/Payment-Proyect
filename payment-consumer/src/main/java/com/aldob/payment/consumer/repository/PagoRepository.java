package com.aldob.payment.consumer.repository;

import com.aldob.payment.consumer.entity.Pagos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PagoRepository extends JpaRepository<Pagos, UUID> {
}
