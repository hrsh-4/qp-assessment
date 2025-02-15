package com.grocery.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRoutingConfiguration {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
            // Grocery Service Routes
            .route("grocery_service_list", r -> r
                .path("/api/grocery-items")
                .uri("lb://grocery-service"))
            .route("grocery_service_item", r -> r
                .path("/api/grocery-items/**")
                .uri("lb://grocery-service"))
            
            // Order Service Routes
            .route("order_service_create", r -> r
                .path("/api/orders")
                .uri("lb://order-service"))
            .route("order_service_user", r -> r
                .path("/api/orders/user/**")
                .uri("lb://order-service"))
            .route("order_service_specific", r -> r
                .path("/api/orders/**")
                .uri("lb://order-service"))
            
            .build();
    }
}
