package com.vinsguru.webflux_playground.tests.sec07;

import com.vinsguru.webflux_playground.tests.sec07.dto.CalculatorResponse;
import com.vinsguru.webflux_playground.tests.sec07.dto.Product;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

import java.util.Map;

public class Lec06QueryParamsTest extends AbstractWebClient{

    private final WebClient client = createWebClient();

    @Test
    public void uriBuilderVariables() {
        var path = "/lec06/calculator";
        var query = "first={first}&second={second}&operation={operation}";
        this.client.get()
                .uri(uriBuilder -> uriBuilder
                        .path(path)
                        .query(query)
                        .build(10, 20, "+"))
                .header("caller-id", "new-value")
                .retrieve()
                .bodyToMono(CalculatorResponse.class)
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }

    @Test
    public void uriBuilderMap() {
        var path = "/lec06/calculator";
        var query = "first={first}&second={second}&operation={operation}";
        var map = Map.of(
                "first", 10,
                "second", 20,
                "operation", "+"
        );

        this.client.get()
                .uri(uriBuilder -> uriBuilder
                        .path(path)
                        .query(query)
                        .build(map))
                .header("caller-id", "new-value")
                .retrieve()
                .bodyToMono(CalculatorResponse.class)
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }
}
