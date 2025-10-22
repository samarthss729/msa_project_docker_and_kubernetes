package com.example.apigateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Component
public class AuthenticationFilter implements GlobalFilter, Ordered {

    private static final List<String> OPEN_API_ENDPOINTS = Arrays.asList(
            "/api/auth/login",
            "/api/auth/register",
            "/api/admin/login",
            "/api/admin/logout",
            "/api/admin/jobs",
            "/api/admin/jobs/",
            "/api/jobs",
            "/api/jobs/",
            "/api/applications",
            "/api/applications/",
            "/api/actuator/health"
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        // Always allow preflight
        if (request.getMethod() == HttpMethod.OPTIONS) {
            return chain.filter(exchange);
        }

        // Skip authentication for open endpoints
        if (isOpenEndpoint(path)) {
            return chain.filter(exchange);
        }

        // Check for Authorization header
        String authHeader = request.getHeaders().getFirst("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        return chain.filter(exchange);
    }

    private boolean isOpenEndpoint(String path) {
        return OPEN_API_ENDPOINTS.stream()
                .anyMatch(endpoint -> path.startsWith(endpoint));
    }

    @Override
    public int getOrder() {
        return -1;
    }
}


