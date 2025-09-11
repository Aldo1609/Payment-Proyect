package com.aldob.payment.consumer.service;

import java.util.Optional;

public interface HealthService {
    Optional<String> readiness();
}

