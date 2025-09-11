package com.aldob.payment.repository;

import com.aldob.payment.entity.Clientes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClientesRepository extends JpaRepository<Clientes, UUID> {
}
