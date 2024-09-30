package com.vinsguru.webflux_playground.sec02.repository;

import com.vinsguru.webflux_playground.sec02.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<Product, Integer> {

    Flux<Product> findByPriceBetween(int lowRange, int highRange);

    Flux<Product> findBy(Pageable pageable);
}
