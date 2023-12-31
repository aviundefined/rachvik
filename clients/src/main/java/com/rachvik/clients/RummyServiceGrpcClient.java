package com.rachvik.clients;

import com.rachvik.clients.grpc.config.Service;
import com.rachvik.games.cards.rummy.services.RummyServiceGrpc;
import com.rachvik.games.cards.rummy.services.RummyServiceGrpc.RummyServiceBlockingStub;
import io.grpc.ManagedChannelBuilder;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RummyServiceGrpcClient implements GrpcClient<RummyServiceBlockingStub> {

  private final ClientConfigReader configReader;

  public RummyServiceBlockingStub getStub() {
    val grpcClientConfig = configReader.getGrpcClientConfig(service());
    return RummyServiceGrpc.newBlockingStub(
        ManagedChannelBuilder.forAddress(grpcClientConfig.getHost(), grpcClientConfig.getPort())
            .usePlaintext()
            .build());
  }

  public Service service() {
    return Service.SERVICE_RUMMY;
  }
}
