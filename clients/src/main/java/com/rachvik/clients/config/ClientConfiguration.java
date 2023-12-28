package com.rachvik.clients.config;

import com.rachvik.clients.service.IdServiceGrpcClient;
import com.rachvik.clients.service.TinyURLServiceGrpcClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({IdServiceGrpcClient.class, TinyURLServiceGrpcClient.class})
public class ClientConfiguration {}
