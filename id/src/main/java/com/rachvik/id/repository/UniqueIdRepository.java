package com.rachvik.id.repository;

import com.rachvik.id.entity.UniqueId;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UniqueIdRepository extends JpaRepository<UniqueId, Long> {

  Optional<UniqueId> findByServiceName(final String serviceName);

  @Query(
      value =
          "INSERT INTO unique_id (service_name, id) VALUES(?1, ?2)  ON CONFLICT (service_name) DO UPDATE SET id = unique_id.id + 1;",
      nativeQuery = true)
  void upsert(final String serviceName, final long id);
}
