package com.rachvik.tinyurl;

import com.rachvik.clients.config.helper.ClientConfigReader;
import com.rachvik.clients.service.IdServiceGrpcClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(value = {ClientConfigReader.class, IdServiceGrpcClient.class})
@SpringBootApplication
public class TinyurlApplication {
  public static void main(String[] args) {
    SpringApplication.run(TinyurlApplication.class, args);
  }
}
