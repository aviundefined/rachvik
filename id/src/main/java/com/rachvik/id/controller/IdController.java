package com.rachvik.id.controller;

import com.rachvik.id.UniqueIdRequest;
import com.rachvik.id.service.IdService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/id/")
@RequiredArgsConstructor
public class IdController {

  private final IdService idService;

  @GetMapping("/unique/{serviceName}")
  public ResponseEntity<Long> getUniqueId(@PathVariable("serviceName") final String serviceName) {
    val request = UniqueIdRequest.newBuilder().setServiceName(serviceName).build();
    val uniqueId = idService.getUniqueId(request);
    return ResponseEntity.ok(uniqueId.getId());
  }
}
