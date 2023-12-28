package com.rachvik.tinyurl.helper;

import com.rachvik.clients.IdServiceGrpcClient;
import com.rachvik.id.UniqueIdRequest;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class URLShortener {
  private static final String elements =
      "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
  private final IdServiceGrpcClient idServiceGrpcClient;

  public String getHashForOriginalUrl(final String originalUrl) {
    val request =
        UniqueIdRequest.newBuilder().setServiceName(idServiceGrpcClient.service().name()).build();
    val uniqueIdResponse = idServiceGrpcClient.getStub().getUniqueId(request);
    return base10ToBase62(uniqueIdResponse.getId());
  }

  public long getIdForHashed(final String hashed) {
    return base62ToBase10(hashed);
  }

  private int base62ToBase10(String s) {
    var n = 0;
    for (var i = 0; i < s.length(); i++) {
      n = n * 62 + convert(s.charAt(i));
    }
    return n;
  }

  private int convert(char c) {
    if (c >= '0' && c <= '9') return c - '0';
    if (c >= 'a' && c <= 'z') {
      return c - 'a' + 10;
    }
    if (c >= 'A' && c <= 'Z') {
      return c - 'A' + 36;
    }
    return -1;
  }

  private String base10ToBase62(long n) {
    val sb = new StringBuilder();
    while (n != 0) {
      sb.insert(0, elements.charAt((int) n % 62));
      n /= 62;
    }
    while (sb.length() != 7) {
      sb.insert(0, '0');
    }
    return sb.toString();
  }
}
