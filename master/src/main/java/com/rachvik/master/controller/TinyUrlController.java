package com.rachvik.master.controller;

import com.rachvik.master.services.TinyUrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/url/")
@RequiredArgsConstructor
public class TinyUrlController {

  private final TinyUrlService tinyUrlService;
  @GetMapping("/short")
  public ResponseEntity<String> getShortUrl(@RequestParam("url") final String originalUrl) {
    return ResponseEntity.ok(tinyUrlService.getShortUrl(originalUrl));
  }

  @GetMapping("/original")
  public ResponseEntity<String> getOriginalUrl(@RequestParam("url") final String shortUrl) {
    return ResponseEntity.ok(tinyUrlService.getOriginalUrl(shortUrl));
  }
}
