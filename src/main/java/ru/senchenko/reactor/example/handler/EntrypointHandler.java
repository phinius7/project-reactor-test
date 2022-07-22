package ru.senchenko.reactor.example.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.Random;

@Component
public class EntrypointHandler {
    private static final Logger logger = LoggerFactory.getLogger(EntrypointHandler.class);
    private static final String TOPIC = "source-topic";

    private final Random random = new Random();
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    public EntrypointHandler(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Transactional
    public void sendMessage() {
        final int data = random.nextInt();
        logger.info("==> Producing message -> {} to {}", data, TOPIC);
        kafkaTemplate.send(TOPIC, String.valueOf(data));
    }

    @Transactional
    public Mono<ServerResponse> generateData(final ServerRequest request) {
        final int count;
        final Optional<String> param = request.queryParam("count");
        if (param.isPresent()) {
            count = Integer.parseInt(param.get());
        } else {
            count = 0;
        }
        logger.info("Sending data...");
        for (int i = 0; i < count; i++) {
            sendMessage();
        }
        logger.info("Finished.");
        return ServerResponse.ok().build();
    }
}
