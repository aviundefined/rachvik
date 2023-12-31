package com.rachvik.rummy.services;

import com.rachvik.clients.IdServiceGrpcClient;
import com.rachvik.games.cards.rummy.services.RummyCreateGameRequest;
import com.rachvik.games.cards.rummy.services.RummyGameResponse;
import com.rachvik.games.cards.rummy.services.RummyMoveRequest;
import com.rachvik.id.UniqueIdRequest;
import com.rachvik.rummy.mappers.MoveMapper;
import com.rachvik.rummy.repository.MoveRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RummyService {

  private final MoveRepository moveRepository;
  private final MoveMapper moveMapper;
  private final IdServiceGrpcClient idServiceGrpcClient;

  public RummyGameResponse createGame(final RummyCreateGameRequest request) {
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
    val move = moveMapper.protoToEntity(id, request.getMove());
    moveRepository.save(move);
    return RummyGameResponse.newBuilder().build();
  }
}
