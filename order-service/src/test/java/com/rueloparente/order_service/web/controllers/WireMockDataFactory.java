package com.rueloparente.order_service.web.controllers;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;

import com.github.tomakehurst.wiremock.client.WireMock;
import java.math.BigDecimal;
import org.springframework.http.MediaType;

class WireMockDataFactory {

    static void mockGetProductByCode(String code, String name, BigDecimal price) {
        stubFor(WireMock.get(urlMatching("/api/products/" + code))
                .willReturn(aResponse()
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withStatus(200)
                        .withBody(
                                """
                        {
                          "code": "%s",
                          "name": "%s",
                          "price": %f
                        }
                        """
                                        .formatted(code, name, price.doubleValue()))));
    }
}
