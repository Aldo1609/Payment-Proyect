package com.aldob.payment.service.impl;

import com.aldob.payment.repository.HealthRepository;
import com.aldob.payment.service.HealthService;
import com.aldob.payment.utils.LogFile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.aldob.payment.utils.AppMessages.OK_STATUS_READINESS;

@Service
@RequiredArgsConstructor
public class HealthServiceImpl implements HealthService {

    private final HealthRepository healthRepository;

    @Override
    public Optional<String> readiness() {

        try {
            healthRepository.testConnexion();
            return Optional.of(OK_STATUS_READINESS);
        } catch (Exception e) {
            LogFile.logError(e.getMessage());
            return Optional.empty();
        }

    }
}
