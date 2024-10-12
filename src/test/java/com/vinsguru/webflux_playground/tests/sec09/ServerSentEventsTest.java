package com.vinsguru.webflux_playground.tests.sec09;

import com.vinsguru.webflux_playground.sec09.dto.ProductDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

@AutoConfigureWebTestClient
@SpringBootTest(properties = "sec=sec09")
public class ServerSentEventsTest {

    private static final Logger log = LoggerFactory.getLogger(ServerSentEventsTest.class);

    @Autowired
    private WebTestClient webClient;

    @Test
    public void serverSentEvents(){
        this.webClient.get()
                .uri("/products/stream/80")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .returnResult(ProductDto.class)
                .getResponseBody()
                .take(3)
                .doOnNext(p -> log.info("Received: {}", p))
                .collectList()
                .as(StepVerifier::create)
                .assertNext(
                        p -> {
                            Assertions.assertEquals(3, p.size());
                            Assertions.assertTrue(p.stream().allMatch(productDto -> productDto.price() <= 80));
                        }
                )
                .expectComplete()
                .verify();
    }
}
