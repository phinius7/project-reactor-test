package ru.senchenko.reactor.example.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class EchoHandler {
    private static final Logger logger = LoggerFactory.getLogger(EchoHandler.class);

    public Mono<ServerResponse> echo(final ServerRequest request) {
        final String path = request.uri().getPath();
        if (path != null && !path.isEmpty()) {
            logger.info("Echo to {}", path);
            return ServerResponse.ok().bodyValue(path);
        } else {
            logger.warn("Request has empty path, request from: {}", request.headers().host());
            return ServerResponse.badRequest().build();
        }
    }
}
