package com.rachvik.rummy;

import com.rachvik.cards.DeckHelper;
import com.rachvik.clients.ClientConfigReader;
import com.rachvik.clients.IdServiceGrpcClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import({DeckHelper.class, ClientConfigReader.class, IdServiceGrpcClient.class})
@SpringBootApplication
public class RummyApplication {

  public static void main(String[] args) {
    SpringApplication.run(RummyApplication.class, args);
  }
}
