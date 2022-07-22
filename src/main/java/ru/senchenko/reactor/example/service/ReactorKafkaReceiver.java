package ru.senchenko.reactor.example.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Service;
import reactor.kafka.receiver.ReceiverRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ReactorKafkaReceiver {
    private static final Logger logger = LoggerFactory.getLogger(ReactorKafkaReceiver.class);

    private final ReactiveKafkaConsumerTemplate<String, String> reactiveKafkaConsumerTemplate;

    private final List<ReceiverRecord<String, String>> localRecords = new ArrayList<>();

    @Autowired
    public ReactorKafkaReceiver(ReactiveKafkaConsumerTemplate<String, String> reactiveKafkaConsumerTemplate) {
        this.reactiveKafkaConsumerTemplate = reactiveKafkaConsumerTemplate;
    }

    @EventListener(ApplicationStartedEvent.class)
    public void receive() {
        reactiveKafkaConsumerTemplate
                .receive()
                .doOnNext(this::process)
                .retry()
                .subscribe();
    }

    private void process(ReceiverRecord<String, String> record) {
        logger.info("<== Received data : {}", record.value());
        localRecords.add(record);
    }

    public List<String> getValue() {
        if (localRecords.size() > 0) {
            List<String> buffer =  localRecords.stream().map(ReceiverRecord::value).collect(Collectors.toList());
            if (localRecords.size() > 10) {
                localRecords.clear();
            }
            return buffer;
        } else {
            return new ArrayList<>();
        }
    }
}
