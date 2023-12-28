package com.rachvik.tinyurl;

import com.rachvik.clients.config.helper.ClientConfigReader;
import com.rachvik.clients.service.IdServiceGrpcClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import({IdServiceGrpcClient.class, ClientConfigReader.class})
class TinyurlApplicationTests {

  @Test
  void contextLoads() {}
}
