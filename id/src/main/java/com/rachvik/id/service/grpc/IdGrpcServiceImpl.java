package com.rachvik.id.service.grpc;

import com.rachvik.id.UniqueIdRequest;
import com.rachvik.id.UniqueIdResponse;
import com.rachvik.id.UniqueIdServiceGrpc;
import com.rachvik.id.service.IdService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IdGrpcServiceImpl extends UniqueIdServiceGrpc.UniqueIdServiceImplBase {

  private final IdService idService;

  @Override
  public void getUniqueId(
      final UniqueIdRequest request, final StreamObserver<UniqueIdResponse> responseObserver) {
    responseObserver.onNext(idService.getUniqueId(request));
    responseObserver.onCompleted();
  }
}
