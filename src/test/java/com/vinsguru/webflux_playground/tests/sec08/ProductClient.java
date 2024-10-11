package com.vinsguru.webflux_playground.tests.sec08;

import com.vinsguru.webflux_playground.sec08.dto.ProductDto;
import com.vinsguru.webflux_playground.sec08.dto.UploadResponse;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ProductClient {

    private final WebClient webClient = WebClient.create("http://localhost:8080");

    public Mono<UploadResponse> uploadProducts(Flux<ProductDto> productDtoFlux) {
        return this.webClient.post()
                .uri("/products/upload")
                .contentType(MediaType.APPLICATION_NDJSON)
                .body(productDtoFlux, ProductDto.class)
                .retrieve()
                .bodyToMono(UploadResponse.class);
    }
}
