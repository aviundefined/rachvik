package com.rachvik.id.service;

import com.rachvik.id.UniqueIdRequest;
import com.rachvik.id.UniqueIdResponse;
import com.rachvik.id.repository.UniqueIdRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IdService {

  private final UniqueIdRepository uniqueIdRepository;
  private static final long START_ID = 100_000_000_000L;

  public UniqueIdResponse getUniqueId(final UniqueIdRequest request) {
    uniqueIdRepository.upsert(request.getServiceName(), START_ID);
    val uniqueId = uniqueIdRepository.findById(request.getServiceName()).orElseThrow();
    return UniqueIdResponse.newBuilder()
        .setServiceName(request.getServiceName())
        .setId(uniqueId.getId())
        .build();
  }
}
