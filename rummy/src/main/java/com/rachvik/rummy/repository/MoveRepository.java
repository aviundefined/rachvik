package com.rachvik.rummy.repository;

import com.rachvik.rummy.entity.Move;
import java.util.List;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface MoveRepository extends CassandraRepository<Move, Long> {
  List<Move> findAllByGameId(final String gameId);
}
