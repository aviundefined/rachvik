package com.rachvik.rummy.services;

import com.rachvik.clients.IdServiceGrpcClient;
import com.rachvik.games.cards.rummy.models.RummyGame;
import com.rachvik.games.cards.rummy.services.RummyCreateGameRequest;
import com.rachvik.games.cards.rummy.services.RummyGameResponse;
import com.rachvik.games.cards.rummy.services.RummyMoveRequest;
import com.rachvik.games.cards.rummy.services.RummyStartGameRequest;
import com.rachvik.id.UniqueIdRequest;
import com.rachvik.rummy.mappers.MoveMapper;
import com.rachvik.rummy.repository.MoveRepository;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RummyService {

  private final MoveRepository moveRepository;
  private final MoveMapper moveMapper;
  private final IdServiceGrpcClient idServiceGrpcClient;
  private final MoveResolver moveResolver;
  private final ConcurrentMap<String, RummyGame.Builder> games = new ConcurrentHashMap<>();

  public RummyGameResponse createGame(final RummyCreateGameRequest request) {
    return null;
  }

  public RummyGameResponse startGame(final RummyStartGameRequest request) {
    return null;
  }

  public RummyGameResponse recordMove(final RummyMoveRequest request) {
    if (!request.hasMove()) {
      throw new RuntimeException("Invalid move request: " + request);
    }
    val id =
        idServiceGrpcClient
            .getStub()
            .getUniqueId(
                UniqueIdRequest.newBuilder().setServiceName(request.getMove().getGameId()).build())
            .getId();
    val gameBuilder =
        this.games.computeIfAbsent(request.getMove().getGameId(), k -> RummyGame.newBuilder());
    this.games.put(
        request.getMove().getGameId(), moveResolver.resolve(gameBuilder, id, request.getMove()));
    val move = moveMapper.protoToEntity(id, request.getMove());
    moveRepository.save(move);
    return RummyGameResponse.newBuilder()
        .setGame(this.games.get(request.getMove().getGameId()))
        .build();
  }
}
