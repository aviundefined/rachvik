package com.rachvik.rummy.repository;

import com.rachvik.rummy.entity.Game;
import com.rachvik.rummy.entity.Move;
import java.util.List;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface GameRepository extends CassandraRepository<Game, String> {}
