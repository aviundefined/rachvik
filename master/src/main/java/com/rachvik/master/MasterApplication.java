package com.rachvik.master;

import com.rachvik.clients.ClientConfigReader;
import com.rachvik.clients.TinyURLServiceGrpcClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import({ClientConfigReader.class, TinyURLServiceGrpcClient.class})
@SpringBootApplication
public class MasterApplication {

  public static void main(String[] args) {
    SpringApplication.run(MasterApplication.class, args);
  }
}
