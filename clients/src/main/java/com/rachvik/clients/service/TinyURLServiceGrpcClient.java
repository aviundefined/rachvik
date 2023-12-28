package com.rachvik.clients.service;

import com.rachvik.clients.config.helper.ClientConfigReader;
import com.rachvik.clients.grpc.config.Service;
import com.rachvik.tinyurl.TinyUrlServiceGrpc;
import com.rachvik.tinyurl.TinyUrlServiceGrpc.TinyUrlServiceBlockingStub;
import io.grpc.ManagedChannelBuilder;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TinyURLServiceGrpcClient implements GrpcClient<TinyUrlServiceBlockingStub> {

  private final ClientConfigReader configReader;

  @Override
  public TinyUrlServiceBlockingStub getStub() {
    val grpcClientConfig = configReader.getGrpcClientConfig(service());
    return TinyUrlServiceGrpc.newBlockingStub(
        ManagedChannelBuilder.forAddress(grpcClientConfig.getHost(), grpcClientConfig.getPort())
            .usePlaintext()
            .build());
  }

  @Override
  public Service service() {
    return Service.SERVICE_TINYURL;
  }
}
