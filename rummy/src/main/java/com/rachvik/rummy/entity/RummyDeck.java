package com.rachvik.rummy.entity;

import java.util.List;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.CassandraType.Name;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@UserDefinedType("rummy_deck")
public class RummyDeck {
  @CassandraType(type = Name.UDT)
  private List<Deck> deck;
}
