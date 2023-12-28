package com.rachvik.tinyurl.helper;

import com.rachvik.clients.service.IdServiceGrpcClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IdBasedURLShortener {
  @Autowired private final IdServiceGrpcClient client;
}
