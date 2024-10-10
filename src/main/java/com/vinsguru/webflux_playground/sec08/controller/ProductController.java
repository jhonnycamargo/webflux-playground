package com.vinsguru.webflux_playground.sec08.controller;

import com.vinsguru.webflux_playground.sec08.dto.ProductDto;
import com.vinsguru.webflux_playground.sec08.dto.UploadResponse;
import com.vinsguru.webflux_playground.sec08.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("products")
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

     @Autowired
        private ProductService productService;

     @PostMapping(value = "upload", consumes = MediaType.APPLICATION_NDJSON_VALUE)
        public Mono<UploadResponse> uploadProducts(@RequestBody Flux<ProductDto> productDtoFlux){
         log.info("Invoked");
            return productService.saveProducts(productDtoFlux.doOnNext(p -> log.info("Received: {}", p)))
                    .then(this.productService.getProductsCount())
                    .map(count -> new UploadResponse(UUID.randomUUID(), count));
        }
}
