package ru.senchenko.reactor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.tools.agent.ReactorDebugAgent;

@SpringBootApplication
public class ReactorTestApp {

    public static void main(final String[] args) {
        ReactorDebugAgent.init();
        SpringApplication.run(ReactorTestApp.class, args);
    }
}
