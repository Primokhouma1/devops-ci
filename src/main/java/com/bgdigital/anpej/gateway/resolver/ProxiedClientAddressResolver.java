package com.bgdigital.anpej.gateway.resolver;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.support.ipresolver.XForwardedRemoteAddressResolver;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;

/**
 * This allow us to fix a rate limiter by Ip address if there is a proxy between the client and us
 * Further, we annotate this KeyResolver with @Primary to give it precedence over the previous implementation
 */
@Primary
@Component
public class ProxiedClientAddressResolver implements KeyResolver {
    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        /*
         * We rely on the X-Forwarded-For header to identify the originating IP address of a client connecting through a proxy server
         * We are passing the value 1 to maxTrustedIndex(), assuming we only have one proxy server
         */
        XForwardedRemoteAddressResolver resolver = XForwardedRemoteAddressResolver.maxTrustedIndex(1);
        InetSocketAddress inetSocketAddress = resolver.resolve(exchange);
        return Mono.just(inetSocketAddress.getAddress().getHostAddress());
    }
}
