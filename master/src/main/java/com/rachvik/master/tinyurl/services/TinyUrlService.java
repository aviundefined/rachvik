package com.rachvik.master.tinyurl.services;

import com.rachvik.clients.TinyURLServiceGrpcClient;
import com.rachvik.tinyurl.OriginalURLRequest;
import com.rachvik.tinyurl.ShortURLRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TinyUrlService {

  private final TinyURLServiceGrpcClient tinyURLServiceGrpcClient;

  public String getShortUrl(final String originalUrl) {
    return tinyURLServiceGrpcClient
        .getStub()
        .getShortURL(ShortURLRequest.newBuilder().setOriginalUrl(originalUrl).build())
        .getShortUrl();
  }

  public String getOriginalUrl(final String shortUrl) {
    return tinyURLServiceGrpcClient
        .getStub()
        .getOriginalURL(OriginalURLRequest.newBuilder().setShortUrl(shortUrl).build())
        .getOriginalUrl();
  }
}
