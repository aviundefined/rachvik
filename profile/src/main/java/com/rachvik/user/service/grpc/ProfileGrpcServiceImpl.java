package com.rachvik.user.service.grpc;

import com.rachvik.profile.service.ProfileServiceGrpc.ProfileServiceImplBase;
import com.rachvik.profile.user.model.GetUserRequest;
import com.rachvik.profile.user.model.GetUserResponse;
import com.rachvik.profile.user.model.SaveUserRequest;
import com.rachvik.profile.user.model.SaveUserResponse;
import com.rachvik.user.service.ProfileService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class ProfileGrpcServiceImpl extends ProfileServiceImplBase {

  private final ProfileService profileService;
  @Override
  public void saveUser(SaveUserRequest request, StreamObserver<SaveUserResponse> responseObserver) {
    responseObserver.onNext(profileService.saveUser(request));
    responseObserver.onCompleted();
  }

  @Override
  public void getUser(GetUserRequest request, StreamObserver<GetUserResponse> responseObserver) {
    responseObserver.onNext(profileService.getUser(request));
    responseObserver.onCompleted();
  }
}
