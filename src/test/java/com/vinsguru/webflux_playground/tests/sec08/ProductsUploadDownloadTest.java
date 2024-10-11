package com.vinsguru.webflux_playground.tests.sec08;

import com.vinsguru.webflux_playground.sec08.dto.ProductDto;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

public class ProductsUploadDownloadTest {

    private static final Logger log = LoggerFactory.getLogger(ProductsUploadDownloadTest.class);
    private final ProductClient productClient = new ProductClient();

    @Test
    public void upload(){
        var flux = Flux.range(1, 10)
                .map(i -> new ProductDto(null, "Product " + i, i))
                .delayElements(Duration.ofSeconds(2));

        this.productClient.uploadProducts(flux)
                .doOnNext(r -> log.info("Received: {}", r))
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }
}
