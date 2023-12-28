package com.rachvik.tinyurl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.rachvik.tinyurl", "com.rachvik.clients"})
@SpringBootApplication
public class TinyurlApplication {
  public static void main(String[] args) {
    SpringApplication.run(TinyurlApplication.class, args);
  }
}
