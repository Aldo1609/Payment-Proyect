package com.aldob.payment.service;

import java.util.Optional;

public interface HealthService {
    Optional<String> readiness();
}

