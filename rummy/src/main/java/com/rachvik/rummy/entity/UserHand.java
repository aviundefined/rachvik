package com.rachvik.rummy.entity;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.CassandraType.Name;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@UserDefinedType("user_hand")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserHand {
  @Column("player")
  private Player player;

  @Column("card")
  private List<Card> card;
}
