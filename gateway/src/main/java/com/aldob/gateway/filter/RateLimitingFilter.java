package com.aldob.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Objects;

@Component
public class RateLimitingFilter extends AbstractGatewayFilterFactory<RateLimitingFilter.Config> {

    private final ReactiveRedisTemplate<String, String> redisTemplate;

    public RateLimitingFilter(ReactiveRedisTemplate<String, String> redisTemplate) {
        super(Config.class);
        this.redisTemplate = redisTemplate;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String ipAddress = Objects.requireNonNull(exchange.getRequest().getRemoteAddress()).getAddress().getHostAddress();
            String key = "rate_limit:" + ipAddress;

            return redisTemplate.opsForValue().increment(key)
                    .flatMap(count -> {
                        if (count == 1) {
                            redisTemplate.expire(key, Duration.ofSeconds(config.getTimeWindow())).subscribe();
                        }

                        if (count > config.getMaxRequests()) {
                            exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
                            return exchange.getResponse().setComplete();
                        }

                        return chain.filter(exchange);
                    });
        };
    }

    public static class Config {
        private int maxRequests = 10;
        private int timeWindow = 60; // segundos

        // Getters y setters
        public int getMaxRequests() { return maxRequests; }
        public void setMaxRequests(int maxRequests) { this.maxRequests = maxRequests; }
        public int getTimeWindow() { return timeWindow; }
        public void setTimeWindow(int timeWindow) { this.timeWindow = timeWindow; }
    }
}