package ru.senchenko.reactor.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({EntrypointProducerConfig.class, ReceiverConfig.class, RoutingConfig.class})
public class AppConfig {

}
