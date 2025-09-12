package com.aldob.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // Health Check - Liveness
                .route("payment-health-liveness", r -> r.path("/payment-service/api/v1/healthcheck/liveness")
                        .filters(f -> f.rewritePath("/payment-service/api/v1/healthcheck/liveness", "/api/v1/healthcheck/liveness"))
                        .uri("lb://payment-service"))

                // Health Check - Readiness
                .route("payment-health-readiness", r -> r.path("/payment-service/api/v1/healthcheck/readiness")
                        .filters(f -> f.rewritePath("/payment-service/api/v1/healthcheck/readiness", "/api/v1/healthcheck/readiness"))
                        .uri("lb://payment-service"))

                // Payments endpoint
                .route("payment-service", r -> r.path("/payment-service/api/v1/payments")
                        .filters(f -> f.rewritePath("/payment-service/api/v1/payments", "/api/v1/payments")
                                .circuitBreaker(config -> config.setName("paymentCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/payment")))
                        .uri("lb://payment-service"))
                .build();
    }
}