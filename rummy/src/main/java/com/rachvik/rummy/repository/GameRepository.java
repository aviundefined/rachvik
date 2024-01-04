package com.rachvik.rummy.repository;

import com.rachvik.rummy.entity.Game;
import com.rachvik.rummy.entity.Move;
import java.util.List;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GameRepository extends MongoRepository<Game, String> {}
