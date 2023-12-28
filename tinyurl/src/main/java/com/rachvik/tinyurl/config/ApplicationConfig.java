package com.rachvik.tinyurl.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@Configuration
@EnableCassandraRepositories("com.rachvik.tinyurl")
public class ApplicationConfig {
}
