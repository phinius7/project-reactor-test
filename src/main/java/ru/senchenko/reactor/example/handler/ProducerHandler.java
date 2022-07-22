package ru.senchenko.reactor.example.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.senchenko.reactor.example.service.ReactorKafkaReceiver;

import java.util.List;
import java.util.Optional;

@Component
public class ProducerHandler {
    private static final Logger logger = LoggerFactory.getLogger(ProducerHandler.class);

    private final ReactorKafkaReceiver reactorKafkaReceiver;

    @Autowired
    public ProducerHandler(ReactorKafkaReceiver reactorKafkaReceiver) {
        this.reactorKafkaReceiver = reactorKafkaReceiver;
    }

    public Mono<ServerResponse> produce(final ServerRequest request) {
        final Optional<String> command = request.queryParam("command");
        logger.info("Got command : {}", command);
        logger.info("Start receive data...");
        List<String> value = reactorKafkaReceiver.getValue();
        logger.info("Forming Response...");
        return (value != null && value.size() > 0) ?
                ServerResponse.ok().bodyValue(value)
                : ServerResponse.noContent().build();
    }
}
