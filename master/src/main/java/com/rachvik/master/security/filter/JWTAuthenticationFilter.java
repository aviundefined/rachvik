package com.rachvik.master.security.filter;

import com.rachvik.common.utils.StringUtils;
import com.rachvik.master.security.entity.User;
import com.rachvik.master.security.service.JWTService;
import com.rachvik.master.security.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {

  private static final String BEARER = "Bearer ";
  private static final int BEARER_INDEX = BEARER.length();

  private final JWTService jwtService;
  private final UserService userService;

  @Override
  protected void doFilterInternal(
      @NonNull final HttpServletRequest request,
      @NonNull final HttpServletResponse response,
      @NonNull final FilterChain filterChain)
      throws ServletException, IOException {
    val authHeader = request.getHeader("Authorization");
    if (StringUtils.isBlank(authHeader) || !authHeader.startsWith(BEARER)) {
      filterChain.doFilter(request, response);
      return;
    }
    val jwt = authHeader.substring(BEARER_INDEX);
    val username = jwtService.extractUsername(jwt);
    // If Username found but SecurityContext is not set then check is token and user is valid,
    // then set the authentication
    if (StringUtils.isNonEmpty(username)
        && SecurityContextHolder.getContext().getAuthentication() == null) {
      final User user = userService.loadUserByUsername(username);
      if (jwtService.isValid(jwt, user)) {
        val authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
      }
    }
    filterChain.doFilter(request, response);
  }
}
