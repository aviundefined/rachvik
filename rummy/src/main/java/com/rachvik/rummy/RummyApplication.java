package com.rachvik.rummy;

import com.rachvik.cards.DeckValidator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import({DeckValidator.class})
@SpringBootApplication
public class RummyApplication {

  public static void main(String[] args) {
    SpringApplication.run(RummyApplication.class, args);
  }
}
