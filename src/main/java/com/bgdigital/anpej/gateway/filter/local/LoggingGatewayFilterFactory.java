package com.bgdigital.anpej.gateway.filter.local;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

/**
 * This filter hashes the request body, placing the value in the X-Hash header.
 * Note: This causes the gateway to be memory constrained.
 * Sample usage: Logging=My Custom Message, true, true
 */
@Component
@Slf4j
public class LoggingGatewayFilterFactory extends
        AbstractGatewayFilterFactory<LoggingGatewayFilterFactory.Config> {


    public LoggingGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            // Pre-processing
            if (config.isPreLogger()) {
                log.info("Pre GatewayFilter logging: "
                        + config.getBaseMessage());
            }
            return chain.filter(exchange)
                    .then(Mono.fromRunnable(() -> {
                        // Post-processing
                        if (config.isPostLogger()) {
                            log.info("Post GatewayFilter logging: "
                                    + config.getBaseMessage());
                        }
                    }));
        };
    }

    // Optional
    // If we want to configure the filter using the compact notation instead
    // Like - Logging=My Custom Message, true, true
    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("baseMessage",
                "preLogger",
                "postLogger");
    }

    @Setter
    @Getter
    @NoArgsConstructor
    public static class Config {
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;
    }
}
