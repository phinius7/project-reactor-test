package ru.senchenko.reactor.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import ru.senchenko.reactor.example.handler.EchoHandler;
import ru.senchenko.reactor.example.handler.ProducerHandler;
import ru.senchenko.reactor.example.handler.EntrypointHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RoutingConfig {

    @Autowired
    private EchoHandler echoHandler;

    @Autowired
    private ProducerHandler produceHandler;

    @Autowired
    private EntrypointHandler entrypointHandler;

    @Bean
    public RouterFunction<ServerResponse> echoRoute() {
        return route(GET("/echo"), echoHandler::echo);
    }

    @Bean
    public RouterFunction<ServerResponse> generateRoute() {
        return route(GET("/generate"), entrypointHandler::generateData);
    }

    @Bean
    public RouterFunction<ServerResponse> produceRoute() {
        return route(POST("/produce"), produceHandler::produce);
    }
}
