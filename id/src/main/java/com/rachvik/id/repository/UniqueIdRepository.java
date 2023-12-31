package com.rachvik.id.repository;

import com.rachvik.id.entity.UniqueId;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UniqueIdRepository extends JpaRepository<UniqueId, String> {
  
  @Modifying
  @Transactional
  @Query(
      value =
          "INSERT INTO unique_id (service_name, id) VALUES(?1, ?2)  ON CONFLICT (service_name) DO UPDATE SET id = unique_id.id + 1;",
      nativeQuery = true)
  void upsert(final String serviceName, final long id);
}
