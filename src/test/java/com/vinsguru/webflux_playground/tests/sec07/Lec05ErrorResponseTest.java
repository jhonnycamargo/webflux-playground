package com.vinsguru.webflux_playground.tests.sec07;

import com.vinsguru.webflux_playground.tests.sec07.dto.CalculatorResponse;
import com.vinsguru.webflux_playground.tests.sec07.dto.Product;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ProblemDetail;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.test.StepVerifier;

public class Lec05ErrorResponseTest extends AbstractWebClient {

    private static final Logger log = LoggerFactory.getLogger(Lec05ErrorResponseTest.class);

    private final WebClient client = createWebClient();

    @Test
    public void handlingError() {
        this.client.get()
                .uri("/lec05/calculator/{a}/{b}", 10, 20)
                .header("operation", "@")
                .retrieve()
                .bodyToMono(CalculatorResponse.class)
                //.onErrorReturn(new CalculatorResponse(0, 0, null, 0.0))
                .doOnError(WebClientResponseException.class, ex -> {
                    log.info("{}", ex.getResponseBodyAs(ProblemDetail.class));
                })
                .onErrorReturn(WebClientResponseException.InternalServerError.class,
                        new CalculatorResponse(0, 0, null, 0.0))
                .onErrorReturn(WebClientResponseException.BadRequest.class,
                        new CalculatorResponse(0, 0, null, -1.0))
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }

}
