package com.rueloparente.order_service.clients;

import com.rueloparente.order_service.dto.ProductWeb;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class ProductServiceClient {
    private static final Logger log = LoggerFactory.getLogger(ProductServiceClient.class);
    private final RestClient restClient;

    ProductServiceClient(RestClient restClient) {
        this.restClient = restClient;
    }

    @Retry(name = "menu-service", fallbackMethod = "getProductByCodeFallback")
    @CircuitBreaker(name = "menu-service")
    public Optional<ProductWeb> getProductByCode(String code) {
        log.info("Fetching product by code: {}", code);
        // TODO: Implement a write-through cache to reduce the number of calls to the product service
        var product =
                restClient.get().uri("/api/products/{code}", code).retrieve().body(ProductWeb.class);
        return Optional.ofNullable(product);
    }

    public Optional<ProductWeb> getProductByCodeFallback(String code, Throwable throwable) {
        log.error("Fallback for fetching product by code: {}", code);
        return Optional.empty();
    }
}
