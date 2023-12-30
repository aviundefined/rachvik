package com.rachvik.clients;

import com.rachvik.clients.grpc.config.Service;
import com.rachvik.profile.service.ProfileServiceGrpc;
import com.rachvik.profile.service.ProfileServiceGrpc.ProfileServiceBlockingStub;
import io.grpc.ManagedChannelBuilder;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProfileServiceGrpcClient implements GrpcClient<ProfileServiceBlockingStub> {

  private final ClientConfigReader configReader;

  public ProfileServiceBlockingStub getStub() {
    val grpcClientConfig = configReader.getGrpcClientConfig(service());
    return ProfileServiceGrpc.newBlockingStub(
        ManagedChannelBuilder.forAddress(grpcClientConfig.getHost(), grpcClientConfig.getPort())
            .usePlaintext()
            .build());
  }

  public Service service() {
    return Service.SERVICE_PROFILE;
  }
}
