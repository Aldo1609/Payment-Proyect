package com.aldob.payment.repository;

import com.aldob.payment.entity.Pagos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PagosRepository extends JpaRepository<Pagos, UUID> {
}
