package com.vinsguru.webflux_playground.sec08.mapper;

import com.vinsguru.webflux_playground.sec08.dto.ProductDto;
import com.vinsguru.webflux_playground.sec08.entity.Product;

public class EntityDtoMapper {

    public static Product toEntity(ProductDto productDto){
        Product product = new Product();
        product.setId(productDto.id());
        product.setDescription(productDto.description());
        product.setPrice(productDto.price());
        return product;
    }

    public static ProductDto toDto(Product product){
        return new ProductDto(product.getId(), product.getDescription(), product.getPrice());
    }
}
