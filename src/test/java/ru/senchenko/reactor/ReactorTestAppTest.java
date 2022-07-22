package ru.senchenko.reactor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.senchenko.reactor.example.config.RoutingConfig;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ReactorTestAppTest {

    @Autowired
    private RoutingConfig routingConfig;

    @Test
    public void shouldAnswerWithTrue() {
        final WebTestClient client = WebTestClient.bindToRouterFunction(routingConfig.echoRoute())
                .build();
        client.get()
                .uri("/echo")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(String.class);
    }

    @TestConfiguration
    @Import(RoutingConfig.class)
    static class Config {

    }
}
