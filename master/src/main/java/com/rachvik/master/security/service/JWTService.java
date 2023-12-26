package com.rachvik.master.security.service;

import com.rachvik.master.security.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JWTService {

  private static final String SECRET_KEY =
      "b86467fb78e057ba9bd6c45764a44cf7cd5b0e9068ccc6162cc29f97fa008a91";

  private static final long EXPIRY_MS = Duration.ofHours(1).toMillis();

  public String extractUsername(final String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public boolean isValid(final String token, final User user) {
    val username = extractUsername(token);
    if (!user.getEmail().equals(username)) {
      return false;
    }
    return !isTokenExpired(token);
  }

  public String generateToken(final User user) {
    return generateToken(user, Collections.emptyMap());
  }

  public String generateToken(final User user, final Map<String, Object> extraClaims) {
    return Jwts.builder()
        .setClaims(extraClaims)
        .setSubject(user.getEmail())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRY_MS))
        .signWith(getSigningKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  private boolean isTokenExpired(final String token) {
    return extractClaim(token, Claims::getExpiration).before(new Date());
  }

  private <T> T extractClaim(final String token, final Function<Claims, T> claimResolver) {
    return claimResolver.apply(extractAllClaims(token));
  }

  private Claims extractAllClaims(final String token) {
    return Jwts.parserBuilder()
        .setSigningKey(getSigningKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  private Key getSigningKey() {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
  }
}
