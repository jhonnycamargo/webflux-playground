package com.vinsguru.webflux_playground.sec06.config;

import com.vinsguru.webflux_playground.sec06.exceptions.CustomerNotFoundException;
import com.vinsguru.webflux_playground.sec06.exceptions.InvalidInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfiguration {

    @Autowired
    private CustomerRequestHandler customerRequestHandler;

    @Autowired
    private ApplicationExceptionHandler exceptionHandler;

    @Bean
    public RouterFunction<ServerResponse> customerRoutes() {
        return RouterFunctions.route()
                //.path("customer", this::customerRoutes2)
                .GET("/customer", this.customerRequestHandler::allCustomers)
                .GET("/customer/paginated", this.customerRequestHandler::paginatedCustomer)
                .GET("/customer/{id}", RequestPredicates.path("*/1?"), this.customerRequestHandler::getCustomer)
                .POST("/customer", this.customerRequestHandler::saveCustomer)
                .PUT("/customer/{id}", this.customerRequestHandler::updateCustomer)
                .DELETE("/customer/{id}", this.customerRequestHandler::deleteCustomer)
                .onError(CustomerNotFoundException.class, this.exceptionHandler::handleException)
                .onError(InvalidInputException.class, this.exceptionHandler::handleException)
//                .filter((request, next) -> {
//                    System.out.println("Before handler");
//                    return next.handle(request);
//                })
                .build();

    }

    //@Bean
//    private RouterFunction<ServerResponse> customerRoutes2() {
//        return RouterFunctions.route()
//                .GET("/paginated", this.customerRequestHandler::paginatedCustomer)
//                .GET("/{id}", this.customerRequestHandler::getCustomer)
//                .GET(this.customerRequestHandler::allCustomers)
//                .build();
//
//    }
}
