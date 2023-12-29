package com.rachvik.master.tinyurl.controller;

import com.rachvik.master.tinyurl.models.UrlRequest;
import com.rachvik.master.tinyurl.models.UrlResponse;
import com.rachvik.master.tinyurl.services.TinyUrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/url/")
@RequiredArgsConstructor
public class TinyUrlController {

  private final TinyUrlService tinyUrlService;

  @PostMapping("/short")
  public ResponseEntity<UrlResponse> getShortUrl(@RequestBody final UrlRequest request) {
    return ResponseEntity.ok(tinyUrlService.getShortUrl(request));
  }

  @PostMapping("/original")
  public ResponseEntity<UrlResponse> getOriginalUrl(@RequestBody final UrlRequest request) {
    return ResponseEntity.ok(tinyUrlService.getOriginalUrl(request));
  }
}
