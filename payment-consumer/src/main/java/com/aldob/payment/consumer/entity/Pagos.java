package com.aldob.payment.consumer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pagos")
public class Pagos {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private Double monto;

    @Column(nullable = false, length = 3)
    private String divisa;

    @Column(nullable = false)
    private String card;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "id_cliente",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_pago_cliente")
    )
    private Clientes cliente;

    @Column(name = "id_transaccion_pasarela")
    private String idTransaccionPasarela;

    @Column(nullable = false, length = 20)
    private String estado;

    @Column(name = "fecha_creacion")
    private Timestamp fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private Timestamp fechaActualizacion;

    @Column(name = "mensaje_error", columnDefinition = "TEXT")
    private String mensajeError;
}