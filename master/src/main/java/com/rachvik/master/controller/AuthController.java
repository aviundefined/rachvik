package com.rachvik.master.controller;

import com.rachvik.master.models.AuthenticationRequest;
import com.rachvik.master.models.AuthenticationResponse;
import com.rachvik.master.models.SignupRequest;
import com.rachvik.master.security.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/")
@RequiredArgsConstructor
public class AuthController {

  private final AuthenticationService authenticationService;
  @PostMapping("/signup")
  public ResponseEntity<AuthenticationResponse> signup(@RequestBody final SignupRequest request) {
      return ResponseEntity.ok(authenticationService.signup(request));
  }

  @PostMapping("/login")
  public ResponseEntity<AuthenticationResponse> login(@RequestBody final AuthenticationRequest request) {
    return ResponseEntity.ok(authenticationService.login(request));
  }
}
