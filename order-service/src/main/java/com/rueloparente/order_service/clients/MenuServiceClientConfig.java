package com.rueloparente.order_service.clients;

import com.rueloparente.order_service.ApplicationProperties;
import java.time.Duration;
import org.springframework.boot.http.client.ClientHttpRequestFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
class MenuServiceClientConfig {
    private final ApplicationProperties properties;

    public MenuServiceClientConfig(ApplicationProperties properties) {
        this.properties = properties;
    }

    @Bean
    RestClient restClient(RestClient.Builder builder, ApplicationProperties properties) {
        ClientHttpRequestFactory requestFactory = ClientHttpRequestFactoryBuilder.simple()
                .withCustomizer(customizer -> {
                    customizer.setConnectTimeout(Duration.ofMillis(properties.menuServiceTimeout()));
                    customizer.setReadTimeout(Duration.ofMillis(properties.menuServiceTimeout()));
                })
                .build();
        return builder.baseUrl(properties.menuServiceUrl())
                .requestFactory(requestFactory)
                .build();
    }
}
