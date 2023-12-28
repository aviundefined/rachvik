package com.rachvik.tinyurl.service;

import com.rachvik.common.utils.StringUtils;
import com.rachvik.tinyurl.OriginalURLRequest;
import com.rachvik.tinyurl.ShortURLRequest;
import com.rachvik.tinyurl.TinyURLResponse;
import com.rachvik.tinyurl.entity.URLMapping;
import com.rachvik.tinyurl.helper.URLShortener;
import com.rachvik.tinyurl.respository.URLMappingRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TinyURLService {
  private static final String SYSTEM_USER_ID = "SYSTEM_USER_ID";
  private static final String BASE_SHORT_URL = "http://tiny.url/";

  private final URLShortener urlShortener;

  private final URLMappingRepository urlMappingRepository;

  public TinyURLResponse getShortURL(final ShortURLRequest request) {
    val user = StringUtils.isBlank(request.getUserId()) ? SYSTEM_USER_ID : request.getUserId();
    // first check if exists
    val optionalURLMapping = urlMappingRepository.findByOriginalURL(request.getOriginalUrl());
    if (optionalURLMapping.isPresent()) {
      return TinyURLResponse.newBuilder()
          .setOriginalUrl(request.getOriginalUrl())
          .setShortUrl(BASE_SHORT_URL + optionalURLMapping.get().getShortURL())
          .setUserId(user)
          .build();
    }
    val hashed = urlShortener.getHashForOriginalUrl(request.getOriginalUrl());
    urlMappingRepository.save(
        URLMapping.builder()
            .shortURL(hashed)
            .originalURL(request.getOriginalUrl())
            .userId(user)
            .build());
    return TinyURLResponse.newBuilder()
        .setOriginalUrl(request.getOriginalUrl())
        .setShortUrl(BASE_SHORT_URL + hashed)
        .setUserId(user)
        .build();
  }

  public TinyURLResponse getOriginalURL(final OriginalURLRequest request) {
    val user = StringUtils.isBlank(request.getUserId()) ? SYSTEM_USER_ID : request.getUserId();
    val hashed = request.getShortUrl().substring(BASE_SHORT_URL.length());
    val urlMapping =
        urlMappingRepository
            .findByShortURL(hashed)
            .orElseThrow(
                () ->
                    new RuntimeException(
                        "Original Url not found for shortUrl: " + request.getShortUrl()));
    return TinyURLResponse.newBuilder()
        .setOriginalUrl(urlMapping.getOriginalURL())
        .setShortUrl(request.getShortUrl())
        .setUserId(user)
        .build();
  }
}
