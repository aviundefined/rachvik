package com.rachvik.kafkademo.controller;

import com.google.protobuf.ByteString;
import com.rachvik.common.message.CommonMessage;
import com.rachvik.kafkademo.producer.KafkaProducer;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class KafkaDemoService {
    private final KafkaProducer producer;

    public void publish() {
        final String value = "Value-" + UUID.randomUUID();
        val commonMessage = CommonMessage.newBuilder()
                .setKey("Key-" + UUID.randomUUID())
                .setTimestamp(System.currentTimeMillis())
                .setValue(ByteString.copyFrom(value.getBytes()))
                .build();
        producer.publish(commonMessage);
    }
}

