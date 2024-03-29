package com.rachvik.rummy.services.grpc;

import com.rachvik.games.cards.rummy.services.RummyCreateGameRequest;
import com.rachvik.games.cards.rummy.services.RummyGameResponse;
import com.rachvik.games.cards.rummy.services.RummyJoinGameRequest;
import com.rachvik.games.cards.rummy.services.RummyMoveRequest;
import com.rachvik.games.cards.rummy.services.RummyPickCardRequest;
import com.rachvik.games.cards.rummy.services.RummyServiceGrpc.RummyServiceImplBase;
import com.rachvik.games.cards.rummy.services.RummyStartGameRequest;
import com.rachvik.rummy.services.RummyService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class RummyGrpcServiceImpl extends RummyServiceImplBase {

  private final RummyService service;

  @Override
  public void createGame(
      final RummyCreateGameRequest request,
      final StreamObserver<RummyGameResponse> responseObserver) {
    responseObserver.onNext(service.createGame(request));
    responseObserver.onCompleted();
  }

  @Override
  public void joinGame(
      final RummyJoinGameRequest request,
      final StreamObserver<RummyGameResponse> responseObserver) {
    responseObserver.onNext(service.joinGame(request));
    responseObserver.onCompleted();
  }

  @Override
  public void startCame(
      final RummyStartGameRequest request,
      final StreamObserver<RummyGameResponse> responseObserver) {
    responseObserver.onNext(service.startGame(request));
    responseObserver.onCompleted();
  }

  @Override
  public void pickCard(
      final RummyPickCardRequest request,
      final StreamObserver<RummyGameResponse> responseObserver) {
    responseObserver.onNext(service.pickCard(request));
    responseObserver.onCompleted();
  }

  @Override
  public void recordMove(
      final RummyMoveRequest request, final StreamObserver<RummyGameResponse> responseObserver) {
    responseObserver.onNext(service.recordMove(request));
    responseObserver.onCompleted();
  }
}
