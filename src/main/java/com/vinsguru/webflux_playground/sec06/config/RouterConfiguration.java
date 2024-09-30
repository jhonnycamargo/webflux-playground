package com.vinsguru.webflux_playground.sec06.config;

import com.vinsguru.webflux_playground.sec06.exceptions.CustomerNotFoundException;
import com.vinsguru.webflux_playground.sec06.exceptions.InvalidInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
                .GET("/customer", this.customerRequestHandler::allCustomers)
                .GET("/customer/paginated", this.customerRequestHandler::paginatedCustomer)
                .GET("/customer/{id}", this.customerRequestHandler::getCustomer)
                .POST("/customer", this.customerRequestHandler::saveCustomer)
                .POST("/customer/{id}", this.customerRequestHandler::updateCustomer)
                .DELETE("/customer/{id}", this.customerRequestHandler::deleteCustomer)
                .onError(CustomerNotFoundException.class, this.exceptionHandler::handleException)
                .onError(InvalidInputException.class, this.exceptionHandler::handleException)
                .build();

    }
}
