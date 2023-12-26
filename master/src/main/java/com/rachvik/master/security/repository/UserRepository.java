package com.rachvik.master.security.repository;

import com.rachvik.master.security.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

  Optional<User> findByEmail(final String email);
}
