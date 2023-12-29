package com.rachvik.kafkademo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/kafka/")
@RequiredArgsConstructor
public class KafkaDemoTestController {

    private final KafkaDemoService service;
    @GetMapping("/demo")
    public ResponseEntity<String> demo() {
        try {
            service.publish();
            return ResponseEntity.ok("Success");
        }catch (final Exception e) {
            return ResponseEntity.ok("Failure");
        }
    }

}
