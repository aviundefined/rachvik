package com.rachvik.tinyurl.helper;

import com.rachvik.clients.IdServiceGrpcClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IdBasedURLShortener {
  private final IdServiceGrpcClient idServiceGrpcClient;
}
