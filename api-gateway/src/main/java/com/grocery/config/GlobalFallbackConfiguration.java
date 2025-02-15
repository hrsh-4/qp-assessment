package com.grocery.config;

import org.springframework.cloud.gateway.filter.factory.RequestRateLimiterGatewayFilterFactory;
import org.springframework.cloud.gateway.handler.predicate.PathRoutePredicateFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Configuration
public class GlobalFallbackConfiguration {

    @Bean
    public Mono<ServerResponse> globalFallbackResponse() {
        return ServerResponse
            .status(HttpStatus.SERVICE_UNAVAILABLE)
            .body(Mono.just("Service is currently unavailable. Please try again later."), String.class);
    }

    @Bean
    public Mono<ServerResponse> rateLimitFallbackResponse() {
        return ServerResponse
            .status(HttpStatus.TOO_MANY_REQUESTS)
            .body(Mono.just("Too many requests. Please slow down."), String.class);
    }
}
