package com.rachvik.master.tinyurl.services;

import com.rachvik.clients.TinyURLServiceGrpcClient;
import com.rachvik.master.tinyurl.models.UrlRequest;
import com.rachvik.master.tinyurl.models.UrlResponse;
import com.rachvik.tinyurl.OriginalURLRequest;
import com.rachvik.tinyurl.ShortURLRequest;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TinyUrlService {

  private final TinyURLServiceGrpcClient tinyURLServiceGrpcClient;

  public UrlResponse getShortUrl(final UrlRequest request) {
    val response =
        tinyURLServiceGrpcClient
            .getStub()
            .getShortURL(ShortURLRequest.newBuilder().setOriginalUrl(request.getUrl()).build());
    return UrlResponse.builder()
        .originalUrl(response.getOriginalUrl())
        .shortUrl(response.getShortUrl())
        .build();
  }

  public UrlResponse getOriginalUrl(final UrlRequest request) {
    val response =
        tinyURLServiceGrpcClient
            .getStub()
            .getOriginalURL(OriginalURLRequest.newBuilder().setShortUrl(request.getUrl()).build());

    return UrlResponse.builder()
        .originalUrl(response.getOriginalUrl())
        .shortUrl(response.getShortUrl())
        .build();
  }
}
