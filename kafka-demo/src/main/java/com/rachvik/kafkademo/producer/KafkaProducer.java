package com.rachvik.kafkademo.producer;

import com.rachvik.common.message.CommonMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    public void publish(final CommonMessage commonMessage) {
        kafkaTemplate.send("demo-topic", commonMessage.getKey(), commonMessage.toByteString().toByteArray());
    }
}
