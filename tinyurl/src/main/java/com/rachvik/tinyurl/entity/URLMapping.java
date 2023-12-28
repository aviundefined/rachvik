package com.rachvik.tinyurl.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("url_mapping")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class URLMapping {

  @PrimaryKey private long id;
  private String shortURL;
  private String originalURL;
  private String userId;
}
