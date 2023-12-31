package com.rachvik.rummy.entity;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.CassandraType.Name;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@UserDefinedType("deck")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Deck {

  @CassandraType(type = Name.UDT)
  private List<Card> card;
}
