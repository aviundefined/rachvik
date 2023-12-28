package com.rachvik.clients;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.rachvik.clients"})
public class ClientsApplication {

  public static void main(String[] args) {
    SpringApplication.run(ClientsApplication.class, args);
  }
}
