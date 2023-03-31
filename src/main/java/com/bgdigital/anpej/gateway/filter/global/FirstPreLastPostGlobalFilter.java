package com.bgdigital.anpej.gateway.filter.global;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * This Global filter is an example of how we can create one, be it a pre globale filter or post global filter
 * Reference: <a href="https://www.baeldung.com/spring-cloud-custom-gateway-filters">Tutorial to create a filter in Spring Cloud Gateway</a>
 */
@Component
@Slf4j
public class FirstPreLastPostGlobalFilter implements GlobalFilter, Ordered {


    @Override
    public Mono<Void> filter(
            ServerWebExchange exchange,
            GatewayFilterChain chain) {
        // The code before return will be executed before the request is forwarded downstream
        log.info("Global Pre Filter executed");
        return chain.filter(exchange).then( // When then is executed when we get a response from downstream
            Mono.fromRunnable(() -> {
                log.info("Last Post Global Filter");
            })
        );
    }

    @Override
    public int getOrder() {
        return -1;
    }



}
