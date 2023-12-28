package com.rachvik.tinyurl.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@Configuration
@EnableCassandraRepositories(basePackages = "com.rachvik.tinyurl.respository")
@ComponentScan(basePackages = { "com.rachvik.clients.service" })
public class ApplicationConfig {}
