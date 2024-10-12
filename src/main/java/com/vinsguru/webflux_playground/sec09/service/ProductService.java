package com.vinsguru.webflux_playground.sec09.service;

import com.vinsguru.webflux_playground.sec09.dto.ProductDto;
import com.vinsguru.webflux_playground.sec09.mapper.EntityDtoMapper;
import com.vinsguru.webflux_playground.sec09.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Flux<ProductDto> saveProducts(Flux<ProductDto> productDtoFlux){
        return productDtoFlux
                .map(EntityDtoMapper::toEntity)
                .as(productRepository::saveAll)
                .map(EntityDtoMapper::toDto);
    }

    public Mono<Long> getProductsCount(){
        return productRepository.count();
    }

    public Flux<ProductDto> allProducts(){
        return productRepository.findAll()
                .map(EntityDtoMapper::toDto);
    }
}
