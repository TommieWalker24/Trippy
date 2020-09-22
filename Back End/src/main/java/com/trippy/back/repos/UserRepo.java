package com.trippy.back.repos;

import com.trippy.back.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository <User, Long> {
    User findByEmail(String email);
    User findByToken(String token);
}
