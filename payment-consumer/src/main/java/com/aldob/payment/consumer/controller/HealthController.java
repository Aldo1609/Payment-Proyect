package com.aldob.payment.consumer.controller;

import com.aldob.payment.consumer.dto.Meta;
import com.aldob.payment.consumer.service.HealthService;
import com.aldob.payment.consumer.utils.GenericResponse;
import com.aldob.payment.consumer.utils.MetaGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static com.aldob.payment.consumer.utils.AppMessages.ERROR_DB_CONNECTION;
import static com.aldob.payment.consumer.utils.AppMessages.OK_STATUS_HEALTH;


@RestController
@RequestMapping("/healthcheck")
@RequiredArgsConstructor
public class HealthController {

    private final MetaGenerator metaGenerator;
    private final HealthService healthService;


    @GetMapping("/liveness")
    public GenericResponse<Boolean> liveness(){
        final Meta meta = metaGenerator.crearMetaObject(HttpStatus.OK, OK_STATUS_HEALTH);
        return new GenericResponse<>(meta, true);
    }

    @GetMapping("/readiness")
    public GenericResponse<Boolean> readiness() {
        final Optional<String> nowSQL = healthService.readiness();
        final boolean status = nowSQL.isPresent();

        final Meta meta = nowSQL
                .map(t -> metaGenerator.crearMetaObject(HttpStatus.OK, t))
                .orElse(metaGenerator.crearMetaObject(HttpStatus.SERVICE_UNAVAILABLE, ERROR_DB_CONNECTION));

        return new GenericResponse<>(meta, status);
    }
}

