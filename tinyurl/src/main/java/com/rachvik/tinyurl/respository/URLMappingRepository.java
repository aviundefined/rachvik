package com.rachvik.tinyurl.respository;

import com.rachvik.tinyurl.entity.URLMapping;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.Optional;

public interface URLMappingRepository extends CassandraRepository<URLMapping, Long> {

    Optional<URLMapping> findByShortURL(final String shortURL);

    Optional<URLMapping> findByOriginalURL(final String originalURL);
}
