package com.rachvik.kafkademo.consumer;

import com.rachvik.common.message.CommonMessage;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaConsumer {
    @KafkaListener(topics = "demo-topic", groupId = "kafka-demo")
    public void consumerMessage(final byte[] bytes) {
        try {
            val commonMessage = CommonMessage.parseFrom(bytes);
            log.info("Message consumed: {}", commonMessage);
        } catch (final Exception e) {
            log.error("Error in consuming the message", e);
            throw new RuntimeException(e);
        }
    }
}
