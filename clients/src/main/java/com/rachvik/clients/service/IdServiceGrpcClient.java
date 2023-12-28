package com.rachvik.clients.service;

import com.rachvik.clients.config.helper.ClientConfigReader;
import com.rachvik.clients.grpc.config.Service;
import com.rachvik.id.UniqueIdServiceGrpc;
import com.rachvik.id.UniqueIdServiceGrpc.UniqueIdServiceBlockingStub;
import io.grpc.ManagedChannelBuilder;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IdServiceGrpcClient {

  private final ClientConfigReader configReader;

  public UniqueIdServiceBlockingStub getStub() {
    val grpcClientConfig = configReader.getGrpcClientConfig(service());
    return UniqueIdServiceGrpc.newBlockingStub(
        ManagedChannelBuilder.forAddress(grpcClientConfig.getHost(), grpcClientConfig.getPort())
            .usePlaintext()
            .build());
  }

  public Service service() {
    return Service.SERVICE_ID;
  }
}
